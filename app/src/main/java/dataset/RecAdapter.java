package dataset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.finco.R;

import java.util.ArrayList;

import entidades.Despesas;
import entidades.Receitas;

public class RecAdapter extends ArrayAdapter<Receitas> {

    public RecAdapter(Context context, ArrayList<Receitas> receitas) {
        super(context, 0, receitas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Receitas receita = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itens, parent, false);
        }
        // Lookup view for data population
        TextView txtid = (TextView) convertView.findViewById(R.id.txtid);
        TextView txtdesc = (TextView) convertView.findViewById(R.id.txtdesc);
        TextView txtval = (TextView) convertView.findViewById(R.id.txtval);
        // Populate the data into the template view using the data object
        txtid.setText(receita.getId().toString());
        txtdesc.setText(receita.getDescricao());
        txtval.setText(receita.getValor().toString());
        // Return the completed view to render on screen
        return convertView;
    }

}
