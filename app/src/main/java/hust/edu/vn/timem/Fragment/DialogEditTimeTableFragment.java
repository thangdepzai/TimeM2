package hust.edu.vn.timem.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hust.edu.vn.timem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogEditTimeTableFragment extends DialogFragment {
    EditText subject_dialog;
    EditText teacher_dialog;
    EditText room_dialog;
     EditText from_time_dialog;
    EditText to_time_dialog;
    EditText to_week_dialog;
     EditText from_week_dialog;
    Button btn_cancel;
    Button btn_save;

    public DialogEditTimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_custom_edit_timetable, container);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy giá trị tự bundle
            String data = getArguments().getString("data", "");
         subject_dialog = view.findViewById(R.id.subject_dialog);
         teacher_dialog = view.findViewById(R.id.teacher_dialog);
         room_dialog = view.findViewById(R.id.room_dialog);
         from_time_dialog = view.findViewById(R.id.from_time_dialog);
         to_time_dialog =view.findViewById(R.id.to_time_dialog);
         to_week_dialog = view.findViewById(R.id.to_week_dialog);
         from_week_dialog =view.findViewById(R.id.from_week_dialog);
         btn_cancel =view .findViewById(R.id.cancel);
         btn_save = view .findViewById(R.id.save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Update clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

}
