package android.rezkyauliapratama.com.dattelunittesttraining;

import android.rezkyauliapratama.com.dattelunittesttraining.Schemas.Event;
import android.rezkyauliapratama.com.dattelunittesttraining.databinding.ListItemMatchBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MatchRvAdapter extends RecyclerView.Adapter<MatchRvAdapter.ViewHolder> {

    private List<Event> events = new ArrayList<>();

    public MatchRvAdapter() {
    }

    public void bindData(List<Event> items){
        events.clear();
        if (items.size() > 0)
            events.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ListItemMatchBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemMatchBinding.bind(itemView);

        }

        public void bind(Event event){
            binding.tvAwayScore.setText(String.valueOf(event.getIntAwayScore()));
            binding.tvHomeScore.setText(String.valueOf(event.getIntHomeScore()));
            binding.tvAwayTeamName.setText(event.getStrAwayTeam());
            binding.tvHomeTeamName.setText(event.getStrHomeTeam());
            binding.tvDate.setText(event.getDateEvent());
        }

    }
}

