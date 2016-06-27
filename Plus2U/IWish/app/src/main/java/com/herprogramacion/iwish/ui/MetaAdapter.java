package com.herprogramacion.iwish.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Meta;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.actividades.DetailActivity;

import java.util.List;

/**
 * Adaptador del recycler view
 */
public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.MetaViewHolder>
        implements ItemClickListener {

    /**
     * Lista de objetos {@link Meta} que representan la fuente de datos
     * de inflado
     */
    private List<Meta> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;
    private String user;


    public MetaAdapter(List<Meta> items, Context context, String user) {
        this.context = context;
        this.items = items;
        this.user = user;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new MetaViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(MetaViewHolder viewHolder, int i) {
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.grupo.setText(items.get(i).getGrupo());

    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        DetailActivity.launch(
                (Activity) context, items.get(position).getId(), user);
    }


    public static class MetaViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView grupo;
        public ItemClickListener listener;

        public MetaViewHolder(View v, ItemClickListener listener) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre);
            grupo = (TextView) v.findViewById(R.id.grupo);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}


interface ItemClickListener {
    void onItemClick(View view, int position);
}
