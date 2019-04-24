package hust.edu.vn.timem.CalendarUtil;

public class LunarCalendarUtil {
        /**
         * Tính số ngày Julius từ một ngày tháng dương lịch
         * @param yy năm
         * @param mm tháng
         * @param dd ngày
         * @return Số ngày Julius
         */
        public static long jdFromDate(int yy, int mm, int dd) {
            long jd = 0;

            int a = (int) Math.floor((14 - mm) / 12);
            int y = yy + 4800 - a;
            int m = mm + 12*a - 3;
            jd = dd + (int) Math.floor((153*m+2)/5) + 365*y + (int) Math.floor(y/4) - (int) Math.floor(y/100) + (int) Math.floor(y/400) - 32045;
            if (jd < 2299161) {
                jd = dd + (int) Math.floor((153*m+2)/5) + 365*y + (int) Math.floor(y/4) - 32083;
            }

            return jd;
        }

        /**
         * Chuyển số ngày Julius thành ngày tháng dương lịch
         * @param jd Số ngày Julius
         * @return Ngày tháng năm dương lịch
         */
        public static YMD jdToDate(long jd) {
            long a, b, c, d, e, m;
            int day, month, year;

            if (jd > 2299160) { //## After 5/10/1582, Gregorian calendar
                a = jd + 32044;
                b = (long) Math.floor((4*a+3)/146097);
                c = a - (long) Math.floor((b*146097)/4);
            } else {
                b = 0;
                c = jd + 32082;
            }
            d = (int) Math.floor((4*c+3)/1461);
            e = c - (int) Math.floor((1461*d)/4);
            m = (int) Math.floor((5*e+2)/153);
            day = (int) (e - Math.floor((153*m+2)/5) + 1);
            month = (int) (m + 3 - 12*Math.floor(m/10));
            year = (int) (b*100 + d - 4800 + Math.floor(m/10));

            return new YMD(year, month, day);

        }

        /**
         * Kiểm tra xem có phải là năm nhuận âm lịch hay không
         * @param yy Năm
         * @param tz Timezone
         * @return True nếu là năm nhuận, False nếu không phải
         */
        public static boolean isLunarLeapYear(int yy, float tz) {
            long a11, b11;
            a11 = getLunarMonth11(yy-1, tz);
            b11 = getLunarMonth11(yy, tz);
            return b11-a11>365?true:false;
        }

        /**
         * Chuyển ngày tháng dương lịch sang ngày tháng âm lịch
         * @param yy Năm dương lịch
         * @param mm Tháng dương lịch
         * @param dd Ngày dương lịch
         * @param tz Timezone
         * @return Lớp YMD chứa ngày tháng năm âm lịch
         */
        public static YMD convertSolar2Lunar(int yy, int mm, int dd, float tz) {
            long dayNumber, k, monthStart, a11, b11;
            int lunarDay, lunarMonth, lunarYear;
            dayNumber = jdFromDate(yy, mm, dd);
            k = (long) Math.floor((dayNumber - 2415021.076998695) / 29.530588853);
            monthStart = getNewMoonDay(k+1, tz);
            if (monthStart > dayNumber) {
                monthStart = getNewMoonDay(k, tz);
            }
            a11 = getLunarMonth11(yy, tz);
            b11 = a11;
            if (a11 >= monthStart) {
                lunarYear = yy;
                a11 = getLunarMonth11(yy-1, tz);
            } else {
                lunarYear = yy+1;
                b11 = getLunarMonth11(yy+1, tz);
            }
            lunarDay = (int) (dayNumber-monthStart+1);
            int diff = (int) Math.floor((monthStart - a11)/29);

            lunarMonth = diff+11;
            if (b11 - a11 > 365) {
                int leapMonthDiff = getLeapMonthOffset(a11, tz);
                if (diff >= leapMonthDiff) {
                    lunarMonth = diff + 10;
                    if (diff == leapMonthDiff) {
                        //lunarLeap = 1;
                    }
                }
            }
            if (lunarMonth > 12) {
                lunarMonth = lunarMonth - 12;
            }
            if (lunarMonth >= 11 && diff < 4) {
                lunarYear -= 1;
            }

            return new YMD(lunarYear, lunarMonth, lunarDay);
        }

        /**
         * Chuyển từ âm lịch sang dương lịch
         * @param lunarYear Năm âm lịch
         * @param lunarMonth Tháng âm lịch
         * @param lunarDay Ngày âm lịch
         * @param isLeapMonth Chuyển tháng nhuận hay tháng không nhuận
         * @param tz Timezone
         * @return Lớp YMD chứa ngày tháng năm dương lịch
         */
        public static YMD convertLunar2Solar(int lunarYear, int lunarMonth, int lunarDay, boolean isLeapMonth, float tz) {
            long k, a11, b11, off, leapOff, leapMonth, monthStart;
            if (lunarMonth < 11) {
                a11 = getLunarMonth11(lunarYear-1, tz);
                b11 = getLunarMonth11(lunarYear, tz);
            } else {
                a11 = getLunarMonth11(lunarYear, tz);
                b11 = getLunarMonth11(lunarYear+1, tz);
            }
            off = lunarMonth - 11;
            if (off < 0) {
                off += 12;
            }
            if (b11 - a11 > 365) {
                leapOff = getLeapMonthOffset(a11, tz);
                leapMonth = leapOff - 2;
                if (leapMonth < 0) {
                    leapMonth += 12;
                }
                if (isLeapMonth && lunarMonth != leapMonth) {
                    return new YMD(0, 0, 0);
                } else if (isLeapMonth || off >= leapOff) {
                    off += 1;
                }
            }
            k = (long) Math.floor(0.5 + (a11 - 2415021.076998695) / 29.530588853);
            monthStart = getNewMoonDay(k+off, tz);
            return jdToDate(monthStart+lunarDay-1);
        }

        /**
         * Tìm tháng nhuận của một năm âm lịch
         * @param year Năm âm lịch
         * @param tz Timezone
         * @return Tháng nhuận của năm âm lịch. Nếu không phải là năm nhuận thì trả về 0
         */
        public static int getLunarLeapMonth(int year, float tz){
            int off, lm;
            if(isLunarLeapYear(year, 7))
            {
                off=getLeapMonthOffset(getLunarMonth11(year-1,  tz),  tz);
                lm = off-2;
                if(lm<0)
                {
                    lm += 12;
                }
                return lm;
            }
            else
            {
                return 0;
            }
        }

        /**
         * Lấy số ngày của một tháng âm lịch
         * @param month Tháng âm lịch
         * @param year Năm âm lịch
         * @param tz Timezone
         * @param isLeapMonth Lấy tháng nhuận hay tháng thường
         * @return Số ngày của tháng
         */
        public static int getNumberOfDaysInLunarMonth(int month, int year, float tz, boolean isLeapMonth) {
            YMD solar1, solar2;

            solar1 = convertLunar2Solar(year, month, 1, isLeapMonth, tz);
            if (month != 12) {
                solar2 = convertLunar2Solar(year, month + 1, 1, isLeapMonth, tz);
            } else {
                solar2 = convertLunar2Solar(year+1, 1, 1, isLeapMonth, tz);
            }

            return (int) (jdFromDate(solar2.year, solar2.month, solar2.day) - jdFromDate(solar1.year, solar1.month, solar1.day));
        }

        /**
         * Lấy năm theo Thiên Can Địa Chi
         * @param year Năm
         * @param tz Timezone
         * @return
         */
        public static String getCanChi(int year, float tz) {
            String[] arrThienCan = {"Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý"};
            String[] arrDiaChi = {"Tí", "Sửu", "Dần", "Mão", "Thìn", "Tị", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"};
            return String.format("%s %s",
                    arrThienCan[((year+6)%10)],
                    arrDiaChi[((year%12+8)%12)]);
        }

        // tinh so Julius cua ngay Soc thu k ke tu 1900
        private static long getNewMoonDay(long k, float tz) {
            double T, T2, T3, dr, Jd1, M, Mpr, F, C1, deltat, JdNew;
            T = k/1236.85; //# Time in Julian centuries from 1900 January 0.5
            T2 = T * T;
            T3 = T2 * T;
            dr = Math.PI/180;
            Jd1 = 2415020.75933 + 29.53058868*k + 0.0001178*T2 - 0.000000155*T3;
            Jd1 = Jd1 + 0.00033*Math.sin((166.56 + 132.87*T - 0.009173*T2)*dr); //# Mean new moon
            M = 359.2242 + 29.10535608*k - 0.0000333*T2 - 0.00000347*T3; //# Sun's mean anomaly
            Mpr = 306.0253 + 385.81691806*k + 0.0107306*T2 + 0.00001236*T3; //# Moon's mean anomaly
            F = 21.2964 + 390.67050646*k - 0.0016528*T2 - 0.00000239*T3; //# Moon's argument of latitude
            C1=(0.1734 - 0.000393*T)*Math.sin(M*dr) + 0.0021*Math.sin(2*dr*M);
            C1 = C1 - 0.4068*Math.sin(Mpr*dr) + 0.0161*Math.sin(dr*2*Mpr);
            C1 = C1 - 0.0004*Math.sin(dr*3*Mpr);
            C1 = C1 + 0.0104*Math.sin(dr*2*F) - 0.0051*Math.sin(dr*(M+Mpr));
            C1 = C1 - 0.0074*Math.sin(dr*(M-Mpr)) + 0.0004*Math.sin(dr*(2*F+M));
            C1 = C1 - 0.0004*Math.sin(dr*(2*F-M)) - 0.0006*Math.sin(dr*(2*F+Mpr));
            C1 = C1 + 0.0010*Math.sin(dr*(2*F-Mpr)) + 0.0005*Math.sin(dr*(2*Mpr+M));
            if (T < -11) {
                deltat= 0.001 + 0.000839*T + 0.0002261*T2 - 0.00000845*T3 - 0.000000081*T*T3;
            } else {
                deltat= -0.000278 + 0.000265*T + 0.000262*T2;
            }
            JdNew = Jd1 + C1 - deltat;
            return (long) Math.floor(JdNew + 0.5 + tz/24);
        }

        // tinh toa do mat troi tren duong hoang dao (0..11)
        private static long getSunLongitude(long jdn, float tz) {
            double T, T2, dr, M, L0, DL, L;
            T = (jdn - 2451545.5 - tz/24) / 36525; // Time in Julian centuries from 2000-01-01 12:00:00 GMT
            T2 = T*T;
            dr = Math.PI/180; // degree to radian
            M = 357.52910 + 35999.05030*T - 0.0001559*T2 - 0.00000048*T*T2; // mean anomaly, degree
            L0 = 280.46645 + 36000.76983*T + 0.0003032*T2; // mean longitude, degree
            DL = (1.914600 - 0.004817*T - 0.000014*T2)*Math.sin(dr*M);
            DL = DL + (0.019993 - 0.000101*T)*Math.sin(dr*2*M) + 0.000290*Math.sin(dr*3*M);
            L = L0 + DL; // true longitude, degree
            L = L*dr;
            L = L - Math.PI*2*(Math.floor(L/(Math.PI*2))); // Normalize to (0, 2*pi)
            return (long) Math.floor(L / Math.PI * 6);
        }

        // tim ngay bat dau thang 11 am lich
        private static long getLunarMonth11(int yy, float tz) {
            long k, off, nm, sunLong;
            off = jdFromDate(yy, 12, 31) - 2415021;
            k = (long) Math.floor(off / 29.530588853);
            nm = getNewMoonDay(k, tz);
            sunLong = getSunLongitude(nm, tz); // sun longitude at local midnight
            if (sunLong >= 9) {
                nm = getNewMoonDay(k-1, tz);
            }
            return nm;
        }

        // tinh offset cua ngay mong 1 cua thang nhuan so voi ngay bat dau thang 11 am lich
        private static int getLeapMonthOffset(long a11, float tz) {
            long k, last, arc;
            int i;

            k = (long) Math.floor((a11 - 2415021.076998695) / 29.530588853 + 0.5);
            last = 0;
            i = 1; // We start with the month following lunar month 11
            arc = getSunLongitude(getNewMoonDay(k+i, tz), tz);
            do {
                last = arc;
                i++;
                arc = getSunLongitude(getNewMoonDay(k+i, tz), tz);
            } while (arc != last && i < 14);
            return i-1;
        }
}
