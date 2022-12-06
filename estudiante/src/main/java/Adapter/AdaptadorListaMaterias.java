package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estudiante.R;

import java.util.List;

import Entidades.Materia;

public class AdaptadorListaMaterias extends RecyclerView.Adapter<AdaptadorListaMaterias.MateriaHolder>{
    Context context;
    List<Materia> lista_materias;
    final AdaptadorListaMaterias.OnClickListener listener;

    public interface OnClickListener{
        void OnClick(Materia item, View vista);

    }

    public AdaptadorListaMaterias(Context context, List<Materia> lista_materias, OnClickListener listener) {
        this.context = context;
        this.lista_materias = lista_materias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MateriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materiaest, parent, false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new MateriaHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaMaterias.MateriaHolder holder, int position) {
        holder.txtMateriaEst.setText( String.valueOf(lista_materias.get(position).getNombre_materia()));
        holder.txtNrcEst.setText( String.valueOf(lista_materias.get(position).getNrc()));
        holder.binData(lista_materias.get(position));
    }

    @Override
    public int getItemCount() {
        return lista_materias.size();
    }

    public class MateriaHolder extends RecyclerView.ViewHolder {

        CardView listMat;
        TextView txtMateriaEst, txtNrcEst;
        View view;

        public MateriaHolder(View itemView) {
            super(itemView);
            txtMateriaEst=itemView.findViewById(R.id.txtMateriaEst);
            txtNrcEst=itemView.findViewById(R.id.txtNrcEst);
            view = itemView;
        }

        void binData(final Materia item){
            listMat = view.findViewById(R.id.listMat);
            listMat.setOnClickListener(view -> {
                listener.OnClick(item,view);
            });
        }
    }


}
