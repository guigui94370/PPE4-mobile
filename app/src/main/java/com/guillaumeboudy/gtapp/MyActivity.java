package com.guillaumeboudy.gtapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



//assetfile descriptor
//getassets().openFd(filename);


//http://www.guillaumeboudy.com/GestionTournoi/


public class MyActivity extends Activity {


    TextView texte;
    Button btnEquipes;
    Button btnSelect;
    Button btnPoules;
    Button btnElim;
    String chaine = "";
    //String tableauChaine[];
    String ListeDesEquipes = "http://www.guillaumeboudy.com/GestionTournoi/ListeDesEquipes.txt";
    String ListeDesEquipesSelectionnees = "http://www.guillaumeboudy.com/GestionTournoi/ListeDesEquipesSelectionnees.txt";
    String ListeDesPoules = "http://www.guillaumeboudy.com/GestionTournoi/ListeDesPoules.txt";
    String ListeDesEliminations = "http://www.guillaumeboudy.com/GestionTournoi/ListeDesEliminations.txt";


    URL url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenetreprincipal_main);

        texte = (TextView) findViewById(R.id.texte);

        btnEquipes = (Button) findViewById(R.id.btnEquipes);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnPoules = (Button) findViewById(R.id.btnPoules);
        btnElim = (Button) findViewById(R.id.btnElim);

        btnEquipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTxt().execute(ListeDesEquipes);
                chaine = "";
                Toast msg = Toast.makeText(MyActivity.this, "Affichage des équipes inscrites", Toast.LENGTH_LONG);
                msg.show();
            }

        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTxt().execute(ListeDesEquipesSelectionnees);
                chaine = "";
                Toast msg = Toast.makeText(MyActivity.this, "Affichage des équipes participantes", Toast.LENGTH_LONG);
                msg.show();
            }

        });

        btnPoules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTxt().execute(ListeDesPoules);
                chaine = "";
                Toast msg = Toast.makeText(MyActivity.this, "Affichage des poules", Toast.LENGTH_LONG);
                msg.show();
            }

        });

        btnElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTxt().execute(ListeDesEliminations);
                chaine = "";
                Toast msg = Toast.makeText(MyActivity.this, "Affichage des éliminations", Toast.LENGTH_LONG);
                msg.show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fenetre_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aide) {
            texte.setText("Aide");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected class DownloadTxt extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... params) {
            try {

                url = new URL(params[0]);


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                InputStreamReader ipsr = new InputStreamReader(bis);
                BufferedReader br = new BufferedReader(ipsr);
                String ligne;
                while ((ligne = br.readLine())!=null){


                    /*if (params[0] == ListeDesEliminations && ligne.contains("[")){
                            chaine += ligne + "\n";
                            String[] tableauChaine = new String[i];
                                tableauChaine[i] = chaine;


                        }
                        //for (String tableau[] : tableauChaine )
                        //tableauChaine[] = {chaine};*/

                    chaine += ligne + "\n";

                }
                br.close();
            } catch (MalformedURLException eMalformed) {
                System.out.println(eMalformed.toString());
            } catch (IOException eIOExcept) {
                System.out.println(eIOExcept.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            texte.setText(chaine);
        }
    }
}