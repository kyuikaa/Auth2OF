package moe.mikaaa.auth2of;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

public class PasswordDetailFragment extends Fragment {

    private String passwordEntry;

    public PasswordDetailFragment(String passwordEntry) {
        this.passwordEntry = passwordEntry;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_detail, container, false);

        TextView detailTextView = view.findViewById(R.id.detailTextView);
        detailTextView.setText(passwordEntry);

        return view;
    }
}