package com.nit3213app.nit3213finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EntityAdapter adapter;
    private final List<Entity> entities = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String keypass = getIntent().getStringExtra("keypass");
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EntityAdapter(entities, this::onEntityClick);
        recyclerView.setAdapter(adapter);

        fetchEntities(keypass);
    }

    private void fetchEntities(String keypass) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Log.d(TAG, "Fetching entities with keypass: " + keypass);

        Request request = new Request.Builder()
                .url("https://nit3213api.onrender.com/dashboard/" + keypass)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DashboardActivity.this, "Failed to load data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "API call failed", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "Raw response: " + responseData);

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        try {
                            DashboardResponse dashboardResponse = gson.fromJson(responseData, DashboardResponse.class);
                            entities.clear();
                            if (dashboardResponse.entities != null && !dashboardResponse.entities.isEmpty()) {
                                entities.addAll(dashboardResponse.entities);
                                adapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                Log.d(TAG, "Successfully loaded " + entities.size() + " entities");
                            } else {
                                Log.w(TAG, "Entities list is null or empty");
                                Toast.makeText(DashboardActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing response", e);
                            Toast.makeText(DashboardActivity.this, "Error processing data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.w(TAG, "Unsuccessful response: " + response.code());
                        Toast.makeText(DashboardActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void onEntityClick(Entity entity) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("technique", entity.technique);
        intent.putExtra("equipment", entity.equipment);
        intent.putExtra("subject", entity.subject);
        intent.putExtra("photographer", entity.pioneeringPhotographer);
        intent.putExtra("year", entity.yearIntroduced);
        intent.putExtra("description", entity.description);
        startActivity(intent);
    }

    private static class EntityAdapter extends RecyclerView.Adapter<EntityViewHolder> {
        private final List<Entity> entities;
        private final EntityClickListener listener;

        EntityAdapter(List<Entity> entities, EntityClickListener listener) {
            this.entities = entities;
            this.listener = listener;
        }

        @Override
        public EntityViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View view = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_entity, parent, false);
            return new EntityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EntityViewHolder holder, int position) {
            Entity entity = entities.get(position);
            holder.bind(entity);
            holder.itemView.setOnClickListener(v -> listener.onEntityClick(entity));
        }

        @Override
        public int getItemCount() {
            return entities.size();
        }
    }
    private static class EntityViewHolder extends RecyclerView.ViewHolder {
        private final TextView techniqueText;
        private final TextView equipmentText;

        EntityViewHolder(View itemView) {
            super(itemView);
            techniqueText = itemView.findViewById(R.id.techniqueText);
            equipmentText = itemView.findViewById(R.id.equipmentText);
        }

        void bind(Entity entity) {
            techniqueText.setText(entity.technique);
            equipmentText.setText("Equipment: " + entity.equipment);
        }
    }
    static class DashboardResponse {
        List<Entity> entities;
        int entityTotal;
    }

    static class Entity {
        String technique;
        String equipment;
        String subject;
        String pioneeringPhotographer;
        int yearIntroduced;
        String description;
    }

    interface EntityClickListener {
        void onEntityClick(Entity entity);
    }
}