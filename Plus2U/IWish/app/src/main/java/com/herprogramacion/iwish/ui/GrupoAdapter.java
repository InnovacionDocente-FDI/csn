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
 * Adaptador del recycler view
 */
public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder>
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


    public GrupoAdapter(List<Grupo> items, Context context, String user, String code) {
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
    public GrupoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new GrupoViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(GrupoViewHolder viewHolder, int i) {
        viewHolder.nombreGrupo.setText(items.get(i).getNombreGrupo());
        viewHolder.nombre.setText(items.get(i).getNombreAsignatura());
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


    public static class GrupoViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView nombreGrupo;
        public ItemClickListener listener;

        public GrupoViewHolder(View v, ItemClickListener listener) {
            super(v);
            nombreGrupo = (TextView) v.findViewById(R.id.nombre);
            nombre = (TextView) v.findViewById(R.id.grupo);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

