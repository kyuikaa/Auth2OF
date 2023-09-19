package moe.mikaaa.auth2of;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PasswordListFragment extends Fragment {

    public PasswordListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.passwordRecyclerView);
        TextView emptyStateText = view.findViewById(R.id.emptyStateText);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PasswordAdapter passwordAdapter = new PasswordAdapter(getDummyPasswordList()); // Replace with your password list
        recyclerView.setAdapter(passwordAdapter);

        // Show empty state text if the password list is empty
        if (passwordAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyStateText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateText.setVisibility(View.GONE);
        }
    }

    private List<String> getDummyPasswordList() {
        // Replace this with your actual list of saved passwords
        List<String> passwordList = new ArrayList<>();
        passwordList.add("Password 1");
        passwordList.add("Password 2");
        passwordList.add("Password 3");
        return passwordList;
    }
}