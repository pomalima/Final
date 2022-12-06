package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalpomalima.R;

import java.util.List;

import Entidades.Alumno;
import Entidades.Materia;

public class AdaptadorListaAlumnos extends RecyclerView.Adapter<AdaptadorListaAlumnos.AlumnoHolder> {
    Context context;
    List<Alumno> lista_alumnos;
    final AdaptadorListaAlumnos.OnClickListener listener;

    public interface OnClickListener{
        void OnClick(Alumno item, View vista);

    }

    public AdaptadorListaAlumnos(Context context, List<Alumno> lista_alumnos, OnClickListener listener) {
        this.context = context;
        this.lista_alumnos = lista_alumnos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlumnoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estudiantes, parent, false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new AlumnoHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaAlumnos.AlumnoHolder holder, int position) {
        holder.txtNombreEstudiante.setText( String.valueOf(lista_alumnos.get(position).getNombre()));
        holder.txtDniEstudiante.setText( String.valueOf(lista_alumnos.get(position).getDni()));
        holder.binData(lista_alumnos.get(position));
    }

    @Override
    public int getItemCount() {
        return lista_alumnos.size();
    }

    public class AlumnoHolder extends RecyclerView.ViewHolder {
        CardView listEst;
        TextView txtNombreEstudiante, txtDniEstudiante;
        View view;

        public AlumnoHolder( View itemView) {
            super(itemView);
            txtNombreEstudiante=itemView.findViewById(R.id.txtNombreEstudiante);
            txtDniEstudiante=itemView.findViewById(R.id.txtDniEstudiante);
            view = itemView;
        }
        void binData(final Alumno item){
            listEst = view.findViewById(R.id.listEst);
            listEst.setOnClickListener(view -> {
                listener.OnClick(item,view);
            });
        }
    }
}
