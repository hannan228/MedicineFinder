package jav.app.medicinefinder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jav.app.medicinefinder.Model.Message;
import jav.app.medicinefinder.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Message> messageList;

    public RecyclerViewAdapter() {
    }

    public RecyclerViewAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        return new ViewHolder(view,context);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Message message = messageList.get(position);
        holder.message.setText(""+message.getMessage());
        holder.date.setText(""+message.getDate());
        holder.sender.setText(""+message.getSender());
        holder.sendTo.setText(""+message.getSendTo());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView message,date,sender,sendTo;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            message = itemView.findViewById(R.id.message2);
            date = itemView.findViewById(R.id.dated);
            sender = itemView.findViewById(R.id.sender2);
            sendTo = itemView.findViewById(R.id.send2);

        }
    }

}
