package ha.rigfox.appforfanzil;  //test-lol
                                //hello from GitHUB
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String URL_SERVER = "http://ricknmorty.ru/sibsu/";

    private EditText IDinput;
    private EditText passwordInput;
    private Button loginButton;
    private CheckBox remember;
    private loginRequest RequestObj;
    private ProgressDialog progress;
    private Button Kbutton;

    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IDinput = (EditText)findViewById(R.id.IDinput);
        passwordInput = (EditText)findViewById(R.id.passwordInput);
        loginButton = (Button)findViewById(R.id.loginbutton);

        remember = (CheckBox)findViewById(R.id.checkBox);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String SavedID = mSettings.getString("ID", "");
        String SavedPass = mSettings.getString("pass", "");

        IDinput.setText(SavedID);
        passwordInput.setText(SavedPass);

        //кнопка
        Kbutton = (Button)findViewById(R.id.Sbutton);
        Kbutton.setOnClickListener((View.OnClickListener) this);
    }




        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }

    public void loginClick(View view) {
        String ID = IDinput.getText().toString();
        String password = passwordInput.getText().toString();

        // Время проверять длину
        if ((ID.length() > 0) && (password.length() > 0)) {
            // Всё ОК
            // Время делать запросы на серверы
            if (remember.isChecked()) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("ID", ID);
                editor.putString("pass", password);
                editor.apply();
            }

            RequestObj = new loginRequest();
            RequestObj.execute(ID, password);
        } else {
            //Сливаем чувака с ошибкой
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.inputerror, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private class loginRequest extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Отключаем кнопку
            loginButton.setEnabled(false);

            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Ждите!");
            progress.setMessage("Приложение не зависло :) ");
            progress.show();
            progress.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            //Принимаем в этот поток пароль и ID
            String ID = params[0];
            String password = params[1];
            //Создаем Http клиент
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost http = new HttpPost(URL_SERVER);

            //Запиливаем отправляемые данные
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", ID));
            nameValuePairs.add(new BasicNameValuePair("password", password));

            String response = "null";

            try {
                http.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //Отправляем
                response = httpclient.execute(http, new BasicResponseHandler());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Шлем ответ в основной поток
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Включаем кнопку
            loginButton.setEnabled(true);

            progress.dismiss();

            //Проверяем ответ
            if (result.equals("null")) {
                //Ответ не получен
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.connect_error, Toast.LENGTH_LONG);
                toast.show();
            } else {
                //Нужно кое-что попарсить
                try {
                    //Парсим JSON
                    JSONObject jObject = new JSONObject(result);
                    //Смотрим на ошибки
                    String error = jObject.getString("error");
                    if (error.equals("login error")) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                R.string.loginerror, Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, Result.class);

                        String name = jObject.getString("name");
                        intent.putExtra("name", name);

                        if (jObject.has("math")) {
                            //Eсли есть результаты по матану
                            JSONObject mathObj = jObject.getJSONObject("math");
                            int MathScore = mathObj.getInt("score");
                            int MathRating = mathObj.getInt("rating");
                            int MathCount = mathObj.getInt("count");
                            //Передаем результаты
                            intent.putExtra("MathScore", MathScore);
                            intent.putExtra("MathRating", MathRating);
                            intent.putExtra("MathCount", MathCount);
                        }

                        if (jObject.has("bio")) {
                            //Eсли есть результаты по биологии
                            JSONObject bioObj = jObject.getJSONObject("bio");
                            int BioScore = bioObj.getInt("score");
                            int BioRating = bioObj.getInt("rating");
                            int BioCount = bioObj.getInt("count");
                            intent.putExtra("BioScore", BioScore);
                            intent.putExtra("BioRating", BioRating);
                            intent.putExtra("BioCount", BioCount);
                        }

                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.jsonerror, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

}
