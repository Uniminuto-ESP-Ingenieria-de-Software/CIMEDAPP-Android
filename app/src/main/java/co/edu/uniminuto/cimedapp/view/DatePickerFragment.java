package co.edu.uniminuto.cimedapp.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private String tipo = "NONE";

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener, String tipo) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        fragment.setTipo(tipo);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog  = new DatePickerDialog(getActivity(), listener, year, month, day);

        // Min and max date
        switch (tipo) {
            case "PAST":
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                c.set(Calendar.YEAR, year - 100);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                break;
            case "FUTURE":
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                c.set(Calendar.YEAR, year + 1);
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        }

        return datePickerDialog;
    }
}
