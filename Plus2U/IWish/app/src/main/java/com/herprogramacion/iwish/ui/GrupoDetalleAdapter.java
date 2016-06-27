package com.herprogramacion.iwish.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Grupo;
import com.herprogramacion.iwish.modelo.Meta;
import com.herprogramacion.iwish.ui.actividades.DetailGroupActivity;

import java.util.List;

/**
 * Created by Nuria on 26/03/2016.
 */
public class GrupoDetalleAdapter extends RecyclerView.Adapter<GrupoDetalleAdapter.GrupoDetalleViewHolder>
        implements ItemClickListener {

    /**
     * Lista de objetos {@link Meta} que representan la fuente de datos
     * de inflado
     */
    private List<Grupo> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;
    private String user;
    private String code;


    public GrupoDetalleAdapter(List<Grupo> items, Context context, String user, String code) {
        this.context = context;
        this.items = items;
        this.user = user;
        this.code = code;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public GrupoDetalleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new GrupoDetalleViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(GrupoDetalleViewHolder viewHolder, int i) {
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.sumaPorAlumno.setText(items.get(i).getSumaPorAlumno());
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        DetailGroupActivity.launch(
                (Activity) context, items.get(position).getId(), user, code);
    }


    public static class GrupoDetalleViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView sumaPorAlumno;
        public ItemClickListener listener;

        public GrupoDetalleViewHolder(View v, ItemClickListener listener) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre);
            sumaPorAlumno = (TextView) v.findViewById(R.id.grupo);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

