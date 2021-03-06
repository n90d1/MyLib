package com.example.nguye.mylibr.Book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguye.mylibr.R;

public class AddBookActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.nguye.mylibr.Book";
    public static EditText edtBookId;
    EditText edtBookName, edtKind, edtpH, edtAuthor, edtPrice, edtNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        linkNet();
    }
    //Link Add book
    public String link(String bookId, String bookName, String kind, String pH, String author, int price, String note){
        //10.18.101.162|| wifi FPT Polytechnic
        //10.0.136.36|| wifi Mang Day KTX
        String linkAddBook = "http://10.0.136.36:3000/addBook?bookId="+bookId+"&bookName="+bookName+"&kind="+kind+"&pH="+pH+"&author="+author+"&price="+price+"&picPre=&note="+note;
        linkAddBook = linkAddBook.replace(" ", "%20");
        return linkAddBook;
    }
    //Thêm menu vào add book
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        return true;
    }
    //Set chức năng cho item trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                try{
                    boolean check = true;
                    String bookId = edtBookId.getText().toString();
                    String bookName = edtBookName.getText().toString();
                    String kind = edtKind.getText().toString();
                    String pH = edtpH.getText().toString();
                    String author = edtAuthor.getText().toString();
                    int price =Integer.parseInt(edtPrice.getText().toString());
                    String note = edtNote.getText().toString();
                    //Kiểm tra rỗng
                    if(bookId.equals("")||bookName.equals("")||kind.equals("")||pH.equals("")||author.equals("")||note.equals("")){
                        Toast.makeText(this, "Vui lòng không bỏ trống bất kì trường nào !", Toast.LENGTH_SHORT).show();
                        check=false;
                    }
                    if (check==true){
                        //Chèn các biến vào link
                        Add(link(bookId, bookName, kind, pH, author, price, note));
                        Intent intentNextBok = new Intent(AddBookActivity.this, ListBookActivity.class);
                        startActivity(intentNextBok);
                    }
                }catch (NumberFormatException nf){
                    Toast.makeText(this, "Lỗi, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Ánh Xạ
    public void linkNet(){
        edtBookId = (EditText) findViewById(R.id.edtBookId);
        edtBookName = (EditText) findViewById(R.id.edtBookName);
        edtKind = (EditText) findViewById(R.id.edtKind);
        edtpH = (EditText) findViewById(R.id.edtpH);
        edtAuthor =(EditText) findViewById(R.id.edtAuthor);
        edtPrice =(EditText) findViewById(R.id.edtPrice);
        edtNote = (EditText) findViewById(R.id.edtNote);
    }
    //Chạy link Add & Check lỗi
    private void Add(String link){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String check = "\"affectedRows\":1";
                if (response.toString().indexOf(check)!=-1) {
                    Toast.makeText(getApplicationContext(),"Đã thêm sách!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Lỗi, vui lòng kiểm tra lại hoặc liên lạc với quản trị viên!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void scannerNext(View v){
        Intent intentSc = new Intent(AddBookActivity.this, ScannerActivity.class);
        intentSc.putExtra(EXTRA_ID, "addBook");
        startActivity(intentSc);
    }

}
