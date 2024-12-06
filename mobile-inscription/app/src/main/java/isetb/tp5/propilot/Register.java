package isetb.tp5.propilot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private EditText e1, e2, e3, e4, e5;
    private Button br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        // Initialisation des éléments de l'interface utilisateur
        e1 = findViewById(R.id.et_email);
        e2 = findViewById(R.id.et_firstname);
        e3 = findViewById(R.id.et_lastname);
        e4 = findViewById(R.id.et_pwd);
        e5 = findViewById(R.id.et_cpwd);
        br = findViewById(R.id.b_register);

        // Gestion du clic sur le bouton d'inscription
        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e1.getText().toString().trim();
                String firstName = e2.getText().toString().trim();
                String lastName = e3.getText().toString().trim();
                String password = e4.getText().toString().trim();
                String confirmPassword = e5.getText().toString().trim();

                // Vérification des champs obligatoires
                if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Register.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return; // Arrêter si un champ est vide
                }

                // Vérification de la correspondance des mots de passe
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Register.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return; // Arrêter si les mots de passe ne correspondent pas
                }

                // Si tout est correct, procéder à l'inscription
                registerUser(new Users(email, firstName, lastName, password));
            }
        });
    }

    // Méthode pour enregistrer un utilisateur via l'API
    private void registerUser(Users user) {
        UserService apiService = Apis.getService(); // Appelle le service via Apis
        Call<Users> call = apiService.registerUser(user);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Register.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Échec de l'inscription !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(Register.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
