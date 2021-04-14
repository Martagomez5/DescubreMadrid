package com.example.descubremadrid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OficinasFragmente extends Fragment {

    private Button btnTelef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.oficinasfragment,container,false);

        btnTelef = (Button)view.findViewById(R.id.Buttontelefono);

        btnTelef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "915787810";

               // startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(phone)));
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        return inflater.inflate(R.layout.oficinasfragment,null);


    }
}
