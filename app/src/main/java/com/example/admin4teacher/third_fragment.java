package com.example.admin4teacher;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import persistencia.UserConsulta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link third_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class third_fragment extends Fragment implements UserConsulta.UserUpdateListener {

    private EditText editTextName, editTextLastName, editTextPhone, editTextEmail;
    RequestQueue rq;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public third_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment third_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static third_fragment newInstance(String param1, String param2) {
        third_fragment fragment = new third_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
TextView labelNombre, labelApellido, labelUsuario, labelTelefono, labelEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        String nombre = bundle.getString("nombre");
        String apellido = bundle.getString("apellido");
        String usuario = bundle.getString("usuario");
        String telefono = bundle.getString("telefono");
        String email = bundle.getString("email");
        String idUser= bundle.getString("idUser");
        View view = inflater.inflate(R.layout.fragment_third_fragment, container, false);
            labelNombre = view.findViewById(R.id.labelName);
            labelApellido = view.findViewById(R.id.labellastname);
            labelUsuario = view.findViewById(R.id.labeluser);
            labelTelefono = view.findViewById(R.id.labelphone);
            labelEmail = view.findViewById(R.id.labelemail);
            labelNombre.setText(nombre+" ");
            labelApellido.setText(apellido);
            labelUsuario.setText("Usuario: "+usuario);
            labelTelefono.setText("Teléfono: "+telefono);
            labelEmail.setText(email);
        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_update, null);

                // Obtén las referencias a los EditText dentro del cuadro de diálogo
                EditText dialogEditTextName = dialogView.findViewById(R.id.editTextName);
                EditText dialogEditTextLastName = dialogView.findViewById(R.id.editTextLastName);
                EditText dialogEditTextPhone = dialogView.findViewById(R.id.editTextPhone);
                EditText dialogEditTextEmail = dialogView.findViewById(R.id.editTextEmail);

                dialogEditTextName.setText(nombre);
                dialogEditTextLastName.setText(apellido);
                dialogEditTextPhone.setText(telefono);
                dialogEditTextEmail.setText(email);

                rq = Volley.newRequestQueue(requireContext());
                // Crea el cuadro de diálogo
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
                dialogBuilder.setTitle("Ingrese los datos");
                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Hola diste aceptar", Toast.LENGTH_SHORT).show();
                        editarU(rq, dialogEditTextName.getText().toString(), dialogEditTextLastName.getText().toString(),
                                dialogEditTextEmail.getText().toString(), dialogEditTextPhone.getText().toString(),
                                usuario, idUser);
                    }
                });
                dialogBuilder.setNegativeButton("Cancelar", null);

                // Muestra el cuadro de diálogo
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });

        return view;
    }

    public void editarU(RequestQueue rq, String name, String lastname, String email, String phone, String user, String iduser){
        UserConsulta usc = new UserConsulta();

        usc.setUserUpdateListener(this);
        usc.editarUsuario(rq, name, lastname, email, phone, user, iduser);
    }


    @Override
    public void onUpdateSuccess() {
        Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateError(String errorMessage) {
        Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
    }
}