package com.imunoz.evaluacion1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{
    public ArrayList<Noticia> noticias;
    public Adaptador(ArrayList<Noticia> noticias){
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);
        return new ViewHolder(view).enlaceAdaptador(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foto.setImageResource(R.drawable.ic_launcher_background);
        holder.titulo.setText(noticias.get(position).getTitulo()  );
        holder.texto.setText(noticias.get(position).getTexto());
        holder.coordenada.setText(noticias.get(position).getCoordenada());

       // holder.texto.setText(reservas.get(position).getTipoCancha());


        holder.noticia = noticias.get(position);

        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.foto.getContext(), "asdf", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView titulo, texto,coordenada;
        private Button botonNumero, botonEliminar;
        private Noticia noticia;
        private Adaptador adaptador;

        public ViewHolder(View itemView){
            super(itemView);

            foto = itemView.findViewById(R.id.foto);
            titulo = itemView.findViewById(R.id.titulo);
            texto = itemView.findViewById(R.id.texto);
            coordenada = itemView.findViewById(R.id.campoCoordenada);

            botonNumero = itemView.findViewById(R.id.botonNumeros);
            botonEliminar = itemView.findViewById(R.id.botonEliminar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Evento click sobre item", Toast.LENGTH_LONG).show();
                }
            });

            botonNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    StringBuilder sb =new StringBuilder();
                    sb.append("Noticia");
                    sb.append("\n"+adaptador.noticias.get(getAdapterPosition()).getTitulo());
                    sb.append("\n"+adaptador.noticias.get(getAdapterPosition()).getTexto());
                    sb.append("\n"+adaptador.noticias.get(getAdapterPosition()).getCoordenada());

                    Toast.makeText(view.getContext(), sb.toString(), Toast.LENGTH_LONG).show();

                }
            });

            botonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adaptador.noticias.remove(getAdapterPosition());
                    adaptador.notifyItemRemoved(getAdapterPosition());
                    adaptador.notifyItemRangeChanged(getAdapterPosition(), adaptador.noticias.size());
                }
            });

        }

        public ViewHolder enlaceAdaptador(Adaptador a){
            this.adaptador = a;
            return this;
        }
    }

}
