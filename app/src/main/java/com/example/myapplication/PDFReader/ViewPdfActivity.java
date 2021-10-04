package com.example.myapplication.PDFReader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.myapplication.Handler.DownloadHandler;
import com.example.myapplication.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ViewPdfActivity extends AppCompatActivity {

    String urls;
    PDFView pdfView;
    ProgressDialog dialog;
    Integer pageNumber = 0;
    String pdfFileName, pdfImageUrl;
    String TAG="PdfActivity";

    ImageView downloadPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfView = findViewById(R.id.abc);
        downloadPDF = (ImageView) findViewById(R.id.downloadPDF);

        final DownloadHandler downloadHandler = new DownloadHandler();

        // Firstly we are showing the progress
        // dialog when we are loading the pdf
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();

        // getting url of pdf using getItentExtra
        urls = getIntent().getStringExtra("url");
        pdfFileName = getIntent().getStringExtra("name");
        pdfImageUrl = getIntent().getStringExtra("imageUrl");

        new RetrivePdfStream().execute(urls);

        downloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(downloadHandler.downloadFile(urls, pdfFileName)){
                    Toast.makeText(getApplicationContext(), "Pdf downloaded successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Please, contact service provider", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
        class RetrivePdfStream extends AsyncTask<String, Void, InputStream>
                implements OnDrawListener, OnTapListener, OnPageChangeListener, OnLoadCompleteListener {

            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream = null;
                try {

                    // adding url
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    // if url connection response code is 200 means ok the execute
                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    }
                }
                // if error return null
                catch (IOException e) {
                    return null;
                }
                return inputStream;
            }

            @Override
            // Here load the pdf and dismiss the dialog box
            protected void onPostExecute(InputStream inputStream) {

                pdfView.fromStream(inputStream)
//                        .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(pageNumber)
                        // allows to draw something on the current page, usually visible in the middle of the screen
                        .onDraw(this)
                        // allows to draw something on all pages, separately for every page. Called only for visible pages
//                        .onDrawAll(onDrawListener)
//                        .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
                        .onPageChange(this)
//                        .onPageScroll(onPageScrollListener)
//                        .onError(onErrorListener)
//                        .onPageError(onPageErrorListener)
//                        .onRender(onRenderListener) // called after document is rendered for the first time
                        // called on single tap, return true if handled, false to toggle scroll handle visibility
                        .onTap(this)
//                        .onLongPress(onLongPressListener)
                        .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                        .password(null)
                        .onLoad(this)
                        .scrollHandle(new DefaultScrollHandle(getApplicationContext()))// to display page number
                        .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                        // spacing between pages in dp. To define spacing color, set view background
                        .spacing(0)
//                        .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
//                        .linkHandler(DefaultLinkHandler)
//                        .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
//                        .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
//                        .pageSnap(false) // snap pages to screen boundaries
//                        .pageFling(false) // make a fling change only a single page like ViewPager
//                        .nightMode(false) // toggle night mode
                        .load();


                dialog.dismiss();
            }

            @Override
            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

            }

            @Override
            public void loadComplete(int nbPages) {
                PdfDocument.Meta meta = pdfView.getDocumentMeta();
                printBookmarksTree(pdfView.getTableOfContents(), "-");

            }

            public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
                for (PdfDocument.Bookmark b : tree) {

                    Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

                    if (b.hasChildren()) {
                        printBookmarksTree(b.getChildren(), sep + "-");
                    }
                }
            }


            @Override
            public boolean onTap(MotionEvent e) {
                Toast.makeText(getApplicationContext(), "Tap", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onPageChanged(int page, int pageCount) {
                pageNumber = page;
                setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
            }
        }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(), PDFContainer_Activity.class);
//        startActivity(intent);
//    }
}
