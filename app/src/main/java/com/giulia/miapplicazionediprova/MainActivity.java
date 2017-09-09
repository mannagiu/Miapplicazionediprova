package com.giulia.miapplicazionediprova;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    int i;
    int j;
    // definisco un ArrayList con il nome delle cartelle e/o file
    ArrayList<String> listp;
   //ARRAY DI IMMAGINI
    ArrayList<Integer> list_i;
    ArrayList<String> list_items;
    ArrayList<Myobject> arrayobj;
    int numoggetti;
    JSONArray jsonRootarray = new JSONArray();
    TextView tv;
    FileInputStream stream = null;
    FileOutputStream fOut = null;
    File filedirectory = null;
    FileChannel fc;
    MappedByteBuffer bb;
    String jsonStr;
    String temp;
    JSONArray parse= null;
    String key;
    int l=0;
    ListView mylist;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView im = (ImageView) findViewById(R.id.ima);
        im.setImageResource(R.drawable.addfolder);
        mylist = (ListView) findViewById(R.id.listv);
        listp = new ArrayList<>();
        list_i = new ArrayList<>();
        list_items = new ArrayList<>();
        arrayobj = new ArrayList<>();
        tv = (TextView) findViewById(R.id.view);
        tv.setText("lili");

        final Myadapter myAdapter = new Myadapter(MainActivity.this, list_i, listp);

        //Myobject o1 = new Myobject("dir p", "dir", false);
        //Myobject o2 = new Myobject("dir2", "dir", false);
        //Myobject o3 = new Myobject("file1", "file", false);


        //use >  int id = c.getInt("duration"); if you want get an int
        //arrayobj.add(o1);
        //arrayobj.add(o2);
        //arrayobj.add(o3);

        //String[] nameproducts = new String[] { "dir1", "dir2", "dir3","file","dir4" };

        //numoggetti = arrayobj.size();

        /*for (i = 0; i < numoggetti; i++) {
            JSONObject newone = new JSONObject();
            try {
                //newone.put("tipo", arrayobj.get(i).getTipo());
                JSONArray arrayinterno=new JSONArray();
                newone.put( arrayobj.get(i).getNome(),arrayinterno);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            listp.add(arrayobj.get(i).getNome());

            if (arrayobj.get(i).getTipo() == "dir") {
                list_i.add(R.drawable.folder);
                jsonRootarray.put(newone);
            } else
                list_i.add(R.drawable.file);

        }*/


        // recupero la lista dal
        // layout
        // creo e istruisco l'adattatore


        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listp);

        // inietto i dati
        //mylist.setAdapter(myAdapter);

      /*  jsonStr = jsonRootarray.toString();
        //tv.setText(jsonStr);
        //adesso creo il file
        filedirectory = new File(Environment.getExternalStorageDirectory(), "Documents/giulia4.json");
        try {

            fOut = new FileOutputStream(filedirectory);

            fOut.write(jsonStr.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */

        filedirectory = new File(Environment.getExternalStorageDirectory(), "Documents/giulia4.json");
        try {
            stream = new FileInputStream(filedirectory);

            try {
               temp="";
                int c;
                while((c=stream.read())!=-1){
                    temp=temp+ Character.toString((char)c);
                }

                tv.setText(temp);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Quello che leggo lo metto in un array json


        try {
            parse = new JSONArray(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();

        for (i = 0; i < parse.length(); i++) {


            try {
                //Estraggo l' oggetto json dall'array, in particolare l'i-esimo oggetto json
                obj = parse.getJSONObject(i);
                //ottengo le chiavi che ci sono nel mio array json
                Iterator iterator = obj.keys();
                while(iterator.hasNext()){
                    String key = (String)iterator.next();
                    //uso le chiavi per aggiungerle alla lista che mi serve poi per rappresentare il tutto
                    listp.add(key);
                    list_i.add(R.drawable.folder);

                }
                //inietto i dati nell' Adapter
                mylist.setAdapter(myAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //
                }//mylist.setAdapter(myAdapter);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                // recupero il titolo memorizzato nella riga tramite l'ArrayAdapter
                String cartella;
                cartella = (String) mylist.getItemAtPosition(pos);
                tv.setText(cartella);

                Intent pagina2 = new Intent(MainActivity.this, Main2Activity.class);
                pagina2.putExtra("dato", cartella);
                startActivity(pagina2);


            }

                    /*INSERIRE QUI DIRETTAMENTE IL FILE DA SCARICARE, RICHIESTA HTTP GIÀ IMPLEMENTATA
                    else{

                    }*/



/*
                PER USARE UN'ALTRA ACTIVITY DEVO CREARE UN INTENT e passargli
                //IL METODO getApplicationContext() , mettere una virgola
                e DOPO LA VIRGOLA METTERE IL NOME DELL'ACTIVITY SUCCESSIVA che abbiamo creato(il nome del file .java) E METTERE
                subito dopo -> .class*/
            //mi fa partire la seconda activity mettendo dentro startActivity il nome dell'intent creato
        });

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_newfolder, null);
                final EditText crea_cartella = (EditText) mView.findViewById(R.id.nome_cartella);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!crea_cartella.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Cartella aggiunta", Toast.LENGTH_SHORT).show();
                            String s;
                            s = crea_cartella.getText().toString();
                            //Myobject o=new Myobject(s,"dir",false);
                            //arrayobj.add(o);
                            listp.add(s);
                            list_i.add(R.drawable.folder);


                            JSONObject newone = new JSONObject();
                            JSONArray arrayinterno = new JSONArray();
                            try {
                                newone.put(s, arrayinterno);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //aggiungo all' array json la cartella
                            parse.put(newone);
                            //aggiorno il file
                            String ciao;
                            ciao = parse.toString();
                            try {

                                fOut = new FileOutputStream(filedirectory);

                                fOut.write(ciao.getBytes());

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    fOut.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                });


                mBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });


                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                }
                });

              //Rileggo tutto
                try {
                stream = new FileInputStream(filedirectory);

                try {
                    temp = "";
                    int c;
                    while ((c = stream.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }

                    tv.setText(temp);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Quello che leggo lo metto in un array json
        //IL PARSING FUNZIONA


        try {
                parse = new JSONArray(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject obj1 = new JSONObject();

        for (i = 0; i < parse.length(); i++) {


                try {
                    //Estraggo l' oggetto json dall'array, in particolare l'i-esimo oggetto json
                    obj1 = parse.getJSONObject(i);
                    //ottengo le chiavi che ci sono nel mio array json
                    Iterator iterator = obj1.keys();

                    while (iterator.hasNext()) {
                        key = (String) iterator.next();
                        //uso le chiavi per aggiungerle alla lista che mi serve poi per rappresentare il tutto

                    }



                    //inietto i dati nell' Adapter
                    //  mylist.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }


}






//POSSO FARE LA LETTURA DEL FILE ANCHE COSÌ,
/*
*   try {
            stream = new FileInputStream(yourFile);

            try {
                fc = stream.getChannel();
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
                tv.setText(jsonStr);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/





