package br.org.unesco.appesca.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.org.unesco.appesca.R;
import br.org.unesco.appesca.control.QuestaoDetailFragment;
import br.org.unesco.appesca.model.ItemMenuLateral;
import br.org.unesco.appesca.util.ConstantesCamaraoRegional;


public class FormularioCamaraoActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.questao_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.questao_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView){

        List<ItemMenuLateral> listaMenuLateral = new ArrayList<ItemMenuLateral>();
        listaMenuLateral.add(new ItemMenuLateral(1, "Identificação do entrevistado", R.layout.questao_identificacao));

        int[] questoesFormulario = ConstantesCamaraoRegional.getQuestoesArray;

        for(int i=1; i<questoesFormulario.length; i++){
            listaMenuLateral.add(new ItemMenuLateral(i, "Questão "+i, questoesFormulario[i]));
        }

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(listaMenuLateral));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<ItemMenuLateral> mValues = new ArrayList<ItemMenuLateral>();

        public SimpleItemRecyclerViewAdapter(List<ItemMenuLateral> items) {

            mValues.add(new ItemMenuLateral(1, "Identificação do entrevistado", R.layout.questao_identificacao));

            int[] questoesFormulario = ConstantesCamaraoRegional.getQuestoesArray;

            for(int i=1; i<questoesFormulario.length; i++){
                mValues.add(new ItemMenuLateral(i, "Questão "+i, questoesFormulario[i]));
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.questao_list_title, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitleView.setText(mValues.get(position).title);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(QuestaoDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.id_layout_inflate));
                        QuestaoDetailFragment fragment = new QuestaoDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.questao_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, QuestaoDetailActivity.class);
                        intent.putExtra(QuestaoDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public ItemMenuLateral mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(R.id.title);
            }

        }
    }
}