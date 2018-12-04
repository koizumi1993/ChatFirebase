package com.fundamentos.abisu.chatfirebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundamentos.abisu.chatfirebase.Clases.RecibirMensaje;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.HolderMensaje> {
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private List<RecibirMensaje> listMensaje=new ArrayList<RecibirMensaje>();
    private Context context;

    public MensajeAdapter(Context context) {
        this.context = context;
    }

    public void addMensaje(RecibirMensaje mensaje){
        listMensaje.add(mensaje);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view_mensajes,viewGroup,false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(HolderMensaje holderMensaje, int i) {
        holderMensaje.msgNombre.setText(listMensaje.get(i).getNombre());
        holderMensaje.msgNombre.setText(listMensaje.get(i).getMensaje());
        //holderMensaje.msgHora.setText((int) listMensaje.get(i).getHora());
        if (listMensaje.get(i).getTipoMensaje().equals("2")){
            holderMensaje.msgFoto.setVisibility(View.VISIBLE);
            holderMensaje.msgMensaje.setVisibility(View.VISIBLE);
            Glide.with(context).load(listMensaje.get(i).getFotoMensaje()).into(holderMensaje.msgFoto);
        }else if (listMensaje.get(i).getTipoMensaje().equals("1")){
            holderMensaje.msgFoto.setVisibility(View.GONE);
            holderMensaje.msgMensaje.setVisibility(View.VISIBLE);
        }
        if (listMensaje.get(i).getFotoPerfil().isEmpty()){
            holderMensaje.msgFotoPerfil.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(listMensaje.get(i).getFotoPerfil()).into(holderMensaje.msgFotoPerfil);
        }
        Long codigoHora = listMensaje.get(i).getHora();
        Date fecha = new Date(codigoHora);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");//a pm o am
        holderMensaje.msgHora.setText(simpleDateFormat.format(fecha));
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

    public class HolderMensaje extends RecyclerView.ViewHolder{
        TextView msgNombre, msgHora, msgMensaje;
        ImageView msgFoto, msgFotoPerfil;

        public HolderMensaje(View itemView) {
            super(itemView);
            msgNombre = (TextView) itemView.findViewById(R.id.msgNombre);
            msgHora = (TextView) itemView.findViewById(R.id.msgHora);
            msgMensaje = (TextView) itemView.findViewById(R.id.msgMensaje);
            msgFoto = (ImageView) itemView.findViewById(R.id.msgFoto);
            msgFotoPerfil = (ImageView) itemView.findViewById(R.id.msgFotoPerfil);
        }
    }
}
