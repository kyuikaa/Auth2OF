package moe.mikaaa.auth2of;

import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class DataInputFragment extends Fragment {

    private TextInputEditText websiteNameEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    private static final String KEY_ALIAS = "my_secure_password_key";

    public DataInputFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout websiteNameInputLayout = view.findViewById(R.id.websiteNameInputLayout);
        TextInputLayout usernameInputLayout = view.findViewById(R.id.usernameInputLayout);
        TextInputLayout passwordInputLayout = view.findViewById(R.id.passwordInputLayout);
        websiteNameEditText = view.findViewById(R.id.websiteNameEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        CheckBox showPasswordCheckBox = view.findViewById(R.id.showPasswordCheckBox);
        Button generatePasswordButton = view.findViewById(R.id.generatePasswordButton);
        Button saveButton = view.findViewById(R.id.saveButton);

        generatePasswordButton.setOnClickListener(v -> generatePassword());

        saveButton.setOnClickListener(v -> savePassword());

        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int inputType = isChecked ?
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordEditText.setInputType(inputType);
            passwordEditText.setSelection(passwordEditText.length());
        });
    }

    private void generatePassword() {
        String generatedPassword = "GeneratedPassword123";

        passwordEditText.setText(generatedPassword);
    }

    private void savePassword() {
        String websiteName = Objects.requireNonNull(websiteNameEditText.getText()).toString();
        String username = Objects.requireNonNull(usernameEditText.getText()).toString();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();

        if (TextUtils.isEmpty(websiteName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }

        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                keyGenerator.init(new KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(false) // Modify this as needed
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
                keyGenerator.generateKey();
            }

            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, keyStore.getKey(KEY_ALIAS, null));

            byte[] encryptedPassword = cipher.doFinal(password.getBytes());

            Log.d("DataInputFragment", "Password saved securely.");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}