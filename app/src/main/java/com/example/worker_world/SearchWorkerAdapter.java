package com.example.worker_world;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchWorkerAdapter extends RecyclerView.Adapter<SearchWorkerAdapter.UDataViewHolder> {
    private Context ctxt;
    private List<Worker> workerlist;
    private customClick ccclick2;
    public  SearchWorkerAdapter(Context ctxt,List<Worker> list,customClick ccclick2)
    {
        this.ctxt=ctxt;
        this.workerlist=list;
        this.ccclick2=ccclick2;
    }
    @NonNull
    @Override
    public UDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ctxt);
        View view=inflater.inflate(R.layout.searchedworker,parent,false);
        UDataViewHolder holder1=new UDataViewHolder(view,ccclick2);
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull UDataViewHolder holder, int position) {
        Worker udata=workerlist.get(position);
        holder.workername.setText(udata.getName());
        holder.workname.setText(udata.getWork());
        holder.cost.setText(udata.getCost());
    }
    @Override
    public int getItemCount() {
        return workerlist.size();
    }

    class UDataViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView workername;
        TextView workname;
        TextView cost;
        customClick cclick;
        public UDataViewHolder(@NonNull View itemView,customClick cclick) {
            super(itemView);
            workername=itemView.findViewById(R.id.workername);
            workname=itemView.findViewById(R.id.workname);
            cost=itemView.findViewById(R.id.cost);
            this.cclick=cclick;
            workername.setOnClickListener(this);
            workname.setOnClickListener(this);
            cost.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cclick.oncustomclick(getAdapterPosition());
        }
    }
    public  interface  customClick{
        void oncustomclick(int position);
    }

}

