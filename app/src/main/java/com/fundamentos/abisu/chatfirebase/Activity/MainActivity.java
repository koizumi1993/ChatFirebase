package com.fundamentos.abisu.chatfirebase.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundamentos.abisu.chatfirebase.Clases.EnviarMensaje;
import com.fundamentos.abisu.chatfirebase.MensajeAdapter;
import com.fundamentos.abisu.chatfirebase.R;
import com.fundamentos.abisu.chatfirebase.Clases.RecibirMensaje;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private ImageButton btnMensajes, btnEnviarFoto;

    public static final int enviarFoto = 1;

    private MensajeAdapter mensajeAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //instancias para almacenar contenido en firebase
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static final int cambiarFoto = 2;
    private String urlPerfil;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nombre = (TextView) findViewById(R.id.nombre);
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensajes = (EditText) findViewById(R.id.txtMensajes);
        btnMensajes = (ImageButton) findViewById(R.id.btnMensajes);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);
        urlPerfil="";

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("chat");//Sala de chat (nombre)

        firebaseStorage = FirebaseStorage.getInstance();

        mensajeAdapter = new MensajeAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(layoutManager);
        rvMensajes.setAdapter(mensajeAdapter);

        btnMensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new EnviarMensaje(txtMensajes.getText().toString(),nombre.getText().toString(),"","1",ServerValue.TIMESTAMP));
                txtMensajes.setText("");
            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"),enviarFoto);
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Seecciona una foto"),cambiarFoto);
            }
        });

        mensajeAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // Log.e("Return", dataSnapshot.getValue(Mensaje.class).getClass().getSimpleName());
                RecibirMensaje mensaje = dataSnapshot.getValue(RecibirMensaje.class);
                mensajeAdapter.addMensaje(mensaje);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setScrollbar() {
        rvMensajes.scrollToPosition(mensajeAdapter.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == enviarFoto && resultCode == RESULT_OK){
            Uri url = data.getData();
            storageReference = firebaseStorage.getReference("imagenes");
            final StorageReference referenceFoto = storageReference.child(url.getLastPathSegment());
            referenceFoto.putFile(url).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return referenceFoto.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri url = task.getResult();
                        EnviarMensaje mensaje = new EnviarMensaje("Abisur ha envia una foto",url.toString(),nombre.getText().toString(),urlPerfil,"2",ServerValue.TIMESTAMP);
                        databaseReference.push().setValue(mensaje);
                    }else{

                    }
                }
            });
        }else if (requestCode == cambiarFoto && resultCode == RESULT_OK){
            final Uri uri = data.getData();
            storageReference = firebaseStorage.getReference("perfiles");
            final StorageReference fotoReferencia = storageReference.child(uri.getLastPathSegment());
            fotoReferencia.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fotoReferencia.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri ur1 = task.getResult();
                        urlPerfil = ur1.toString();
                        EnviarMensaje mensaje = new EnviarMensaje("Abisur ha actualizado su foto de perfil",ur1.toString(),nombre.getText().toString(),urlPerfil,"2",ServerValue.TIMESTAMP);
                        databaseReference.push().setValue(mensaje);
                        Glide.with(MainActivity.this).load(uri.toString()).into(fotoPerfil);
                    }else{
                    }
                }
            });
        }
    }
}
