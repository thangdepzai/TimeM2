package hust.edu.vn.timem.Data;

import java.sql.Struct;


public class TTData {
    static public String TIME_TABLE_NAME; // Name of time table
    static public int TIME_TABLE_ID;

    static public String GET_TIME_TABLE_ID = "TIME_TABLE_ID";
    static public String GET_TIME_TABLE_TIME_ID = "TIME_TABLE_TIME_ID";
    static public String GET_TIME_TABLE_NAME = "TIME_TABLE_NAME";
    static public String GET_TIME_TABLE_START_TIME = "GET_START_TIME";
    static public String GET_TIME_TABLE_END_TIME = "GET_END_TIME";
    static public String GET_TIME_TABLE_DATE = "GET_DATE";

    static public int GET_TIME_TABLE_CODE = 1024;
    static public int GET_ANOTHER_TIME_TABLE = 1012;
    static public int RESULT_OK = 1;
    static public int RESULT_FAIL = 0;

    public enum Day {
        MON(0),
        TUE(1),
        WED(2),
        THR(3),
        FRI(4);

        private int value;

        private Day() {

        }

        private Day(int value) {
            this.value = value;
        }

        public int getInt() {
            return value;
        }
    }
}

