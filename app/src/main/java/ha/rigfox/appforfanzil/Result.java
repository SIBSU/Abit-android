package ha.rigfox.appforfanzil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Result extends Activity {

    private ArrayList<HashMap<String, Object>> mResultList;
    private static final String TITLE = "subjectname";
    private static final String DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ListView listView = (ListView)findViewById(R.id.listView);
        TextView TextName = (TextView)findViewById(R.id.name);

        // создаем массив списков
        mResultList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hm;

        Intent sendIntent = getIntent();
        //Получаем результаты

        //Тут формируем их

        String name = sendIntent.getStringExtra("name");
        TextName.setText(name);

        if (sendIntent.hasExtra("MathScore")) {
            int MathScore = sendIntent.getIntExtra("MathScore", 0);
            int MathRating = sendIntent.getIntExtra("MathRating", 0);
            int MathCount = sendIntent.getIntExtra("MathCount", 0);

            hm = new HashMap<String, Object>();
            hm.put(TITLE, "Прикладная математика и информатика");
            hm.put(DESCRIPTION, "Кол-во баллов: "+String.valueOf(MathScore)+ "\n" +
                    "Место в рейтинге: "+String.valueOf(MathRating) + " из " + String.valueOf(MathCount));
            mResultList.add(hm);
        }
        if (sendIntent.hasExtra("BioScore")) {
            int BioScore = sendIntent.getIntExtra("BioScore", 0);
            int BioRating = sendIntent.getIntExtra("BioRating", 0);
            int BioCount = sendIntent.getIntExtra("BioCount", 0);

            hm = new HashMap<String, Object>();
            hm.put(TITLE, "Биология");
            hm.put(DESCRIPTION, "Кол-во баллов: "+String.valueOf(BioScore)+ "\n" +
                    "Место в рейтинге: "+String.valueOf(BioRating) + " из " + String.valueOf(BioCount));
            mResultList.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mResultList,
                R.layout.list_item, new String[]{TITLE, DESCRIPTION},
                new int[]{R.id.text1, R.id.text2});

        listView.setAdapter(adapter);

    }
}
