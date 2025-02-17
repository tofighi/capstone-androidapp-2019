package com.mcclelland.scott.derailmentreportchatbotservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GeneralQueryResultSpecific extends AppCompatActivity {

    DrawerLayout globalNavDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_query_result_specific);

        globalNavDrawerLayout = findViewById(R.id.drawer_layout_generalQueryResultSpecific);

        NavigationView navigationView = findViewById(R.id.navigation_menu);
        final ImageButton globalNavImage = findViewById(R.id.btnGlobalNav);

        globalNavDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                globalNavImage.setImageResource(R.drawable.close_icon);

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                globalNavImage.setImageResource(R.drawable.menu_icon);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.conversationNavItem: {
                        globalNavDrawerLayout.closeDrawer(GravityCompat.START);
                        globalNavImage.setImageResource(R.drawable.menu_icon);
                        Intent intent = new Intent(GeneralQueryResultSpecific.this, Conversation.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.generalQueryNavItem: {
                        globalNavDrawerLayout.closeDrawer(GravityCompat.START);
                        globalNavImage.setImageResource(R.drawable.menu_icon);
                        Intent intent = new Intent(GeneralQueryResultSpecific.this, GeneralQuery.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.uploadDocumentNavItem: {
                        globalNavDrawerLayout.closeDrawer(GravityCompat.START);
                        globalNavImage.setImageResource(R.drawable.menu_icon);
                        Intent intent = new Intent(GeneralQueryResultSpecific.this, UploadDocument.class);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            }
        });

        globalNavImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalNavImage.setImageResource(R.drawable.close_icon);
                globalNavDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Load in the DocumentDetails object that was selected in the GeneralQueryResult Activity
        final DocumentDetails chosenDocument = (DocumentDetails) getIntent().getSerializableExtra("documentDetails");
        final String generalQuery = getIntent().getExtras().getString("query");
        //Load in empty views to be populated based on the DocumentDetails object
        TextView txtFilename = findViewById(R.id.txtFilename);
        TextView txtPassageCount = findViewById(R.id.txtPassageCount);
        TextView txtText = findViewById(R.id.txtText);
        //Button to start new conversation
        Button btnStartNewConversation = findViewById(R.id.btnStartNewConversation);
        //Populate empty views with appropriate data
        txtFilename.setText(chosenDocument.getFilename());
        txtPassageCount.setText(Integer.toString(chosenDocument.getPassageCount()));
        txtText.setText(chosenDocument.getText());

        btnStartNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send this document's Watson Discovery ID and Filename to the Conversation Activity
                startSpecificConversation(chosenDocument.getId(), chosenDocument.getFilename(), generalQuery);
            }
        });
    }

    //Packaging data to send to Conversation Activity
    private void startSpecificConversation(String documentId, String documentFilename, String generalQuery){
        Bundle contentForConversation = new Bundle();
        contentForConversation.putString("documentId", documentId);
        contentForConversation.putString("documentFilename", documentFilename);
        contentForConversation.putString("query", generalQuery);
        Intent intent = new Intent(GeneralQueryResultSpecific.this, Conversation.class);
        intent.putExtras(contentForConversation);
        startActivity(intent);
    }
}
