package ha.rigfox.appforfanzil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultView = (TextView) findViewById(R.id.resulttext);

        Intent sendIntent = getIntent();
        //Получаем результаты

        //Тут формируем их
        String ResultView = "";

        String name = sendIntent.getStringExtra("name");
        ResultView += name + "\n\n";

        if (sendIntent.hasExtra("MathScore")) {
            int MathScore = sendIntent.getIntExtra("MathScore", 0);
            int MathRating = sendIntent.getIntExtra("MathRating", 0);
            ResultView += "Прикл. математика и информатика\n" + "Количество баллов: " + String.valueOf(MathScore) + "\n";
            ResultView += "Место в рейтинге: " + String.valueOf(MathRating) + "\n\n";

            if ( MathRating <= 8 )
            {
                ResultView += "Вы проходите на бюджет к нам, на ПМиИ!\n" +
                        "\n";
            }
            else
            {
                ResultView += "К сожалению, Вы не проходите на бюджет по данному направлению.\n" +
                        "\n";
            }
        }
        if (sendIntent.hasExtra("BioScore")) {
            int BioScore = sendIntent.getIntExtra("BioScore", 0);
            int BioRating = sendIntent.getIntExtra("BioRating", 0);
            ResultView += "Биология\n" + "Количество баллов: " + String.valueOf(BioScore) + "\n";
            ResultView += "Место в рейтинге: " + String.valueOf(BioRating) + "\n\n";

            if ( BioRating <= 8 )
            {
                ResultView += "Вы проходите на бюджет по напр. Биология!";
            }
            else
            {
                ResultView += "К сожалению, Вы не проходите на бюджет по данному направлению\n" +
                        "\n";
            }
        }

        //Отображаем
        resultView.setText(ResultView);
    }
}
