package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estudiante.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import Entidades.Nota;

public class AdaptadorListaNotas extends RecyclerView.Adapter<AdaptadorListaNotas.NotaHolder> {

    Context context;
    List<Nota> lista_notas;

    public AdaptadorListaNotas(Context context, List<Nota> lista_notas) {
        this.context = context;
        this.lista_notas = lista_notas;
    }

    @NonNull
    @NotNull
    @Override
    public NotaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notamotivo, parent, false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new NotaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotaHolder holder, int position) {
        holder.txtObs_Est.setText( String.valueOf(lista_notas.get(position).getObservacion()));
        holder.txtNota_Est.setText( String.valueOf(lista_notas.get(position).getNota()));
    }

    @Override
    public int getItemCount() {
        return lista_notas.size();
    }

    public class NotaHolder extends RecyclerView.ViewHolder {
        TextView txtObs_Est, txtNota_Est;
        View view;

        public NotaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtObs_Est=itemView.findViewById(R.id.txtObs_Est);
            txtNota_Est=itemView.findViewById(R.id.txtNota_Est);
            view = itemView;
        }
    }
}
