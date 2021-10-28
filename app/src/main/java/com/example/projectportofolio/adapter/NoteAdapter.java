package com.example.projectportofolio.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectportofolio.Note.NoteDetail;
import com.example.projectportofolio.R;
import com.example.projectportofolio.RealmHelper;
import com.example.projectportofolio.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private List<NoteModel> noteModels;
    Context context;
    Integer id;
    RealmHelper realmHelper;
    View view;

    public NoteAdapter(Context context, List<NoteModel> noteModels, RealmHelper realmHelper){
        this.context = context;
        this.noteModels = noteModels;
        this.realmHelper = realmHelper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_note, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NoteModel model = noteModels.get(position);
        holder.title.setText(model.getTitleNote());
        holder.desc.setText(model.getDescNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NoteDetail.class);
                intent.putExtra("id",model.getId().toString());
                intent.putExtra("title",model.getTitleNote());
                intent.putExtra("desc",model.getDescNote());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView title,desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            view = itemView;
            title = itemView.findViewById(R.id.tv_title_note);
            desc = itemView.findViewById(R.id.tv_desc_note);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1, 1, "Delete");
            id = noteModels.get(getAdapterPosition()).getId();
            Delete.setOnMenuItemClickListener(onClickmenu);
        }
    }

    private final MenuItem.OnMenuItemClickListener onClickmenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case 1:
                    //Do stuff
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Delete data");
                    builder.setMessage("Are you sure ?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            realmHelper.delete(id);
                            notifyItemRemoved(id);
                            notifyItemRangeChanged(id, noteModels.size());
                            notifyDataSetChanged();
                            Toast.makeText(context,"Posisi  "+id+" dihapus",Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
            }
            return true;
        }
    };
}
