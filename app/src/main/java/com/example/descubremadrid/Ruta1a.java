package com.example.descubremadrid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.api.optimization.v1.MapboxOptimization;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ruta1a extends AppCompatActivity implements OnMapReadyCallback,
        Callback<DirectionsResponse>, PermissionsListener {

    private MapView mapView;
    Point origen;
    Point destino;
    Point intermedio;
    Point intermedio2;
    Point intermedio3;
    Point intermedio4;
    Point intermedio5;
    Point intermedio6;
    Point intermedio7;
    Point intermedio8;
    Point intermedio9;
    Point intermedio10;
    Point intermedio11;
    Point intermedio12;
    Point point2;
    private PermissionsManager permissionsManager;
    RouteProgress routeProgress;
    NavigationViewOptions navigationViewOptions;
    AlertDialog alert;
    ArrayList<Point> puntos;




    MapboxMap mapbox;
    DirectionsRoute curreDirectionsRoute;
    MapboxNavigation navigation;
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    MapboxOptimization mapboxOptimization;
    MapboxDirections client;
    Button boton;
    LocationComponent locationComponent;
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.token));
        setContentView(R.layout.activity_ruta1a);
        mapView = findViewById(R.id.mapView);
        boton = findViewById(R.id.button);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        navigation = new MapboxNavigation(getApplicationContext(), getString(R.string.token));

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ruta();
            }
        });
    }



    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapbox = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                localizacion(style);
                intermedio7 = Point.fromLngLat(-3.7026389596477935, 40.41717157791179);
                destino = Point.fromLngLat(-3.7178982460296903, 40.42477300612053);//Templo Debod
                intermedio = Point.fromLngLat(-3.7026389596477935, 40.41717157791179);//sol
                intermedio2 = Point.fromLngLat( -3.7074867306870987,40.41554367413904);//Plaza Mayor
                intermedio3 = Point.fromLngLat(-3.709512032990105,40.41817589481854);//Plaza Opera
                intermedio4= Point.fromLngLat(-3.710578002221162,40.41828264296649);//Teatro real
                intermedio5 = Point.fromLngLat( -3.7145305444113363, 40.415838855057);//Catedral Almudena
                intermedio6 =Point.fromLngLat(-3.714312002083042, 40.41811017574655);//Palacio Real



                locationComponent = mapbox.getLocationComponent();
                locationComponent.activateLocationComponent(
                        LocationComponentActivationOptions.builder(Ruta1a.this, style).build());




                //Toast.makeText(getApplicationContext(),locationComponent.getLastKnownLocation().getLongitude()+"+"+locationComponent.getLastKnownLocation().getLatitude(),Toast.LENGTH_LONG);
                iniciar(style);
                iniciarLayout(style);
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(destino.latitude(), destino.longitude()))
                        .zoom(10)
                        .tilt(13)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

                ruta(mapboxMap, intermedio7, destino, intermedio, intermedio2,intermedio3,intermedio4, intermedio5, intermedio6);

            }
        });
    }


    private void iniciarLayout(@NonNull Style style) {


        LineLayer lineLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

        lineLayer.setProperties(
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                PropertyFactory.lineWidth(5f),
                PropertyFactory.lineColor(Color.parseColor("#0000ff")));
        style.addLayer(lineLayer);
        style.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.mapbox_marker_icon_default)));

        style.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(

                PropertyFactory.iconImage(RED_PIN_ICON_ID),
                PropertyFactory.iconIgnorePlacement(true),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconOffset(new Float[]{0f, -9f})
        ));




    }

    private void iniciar(@NonNull Style style) {
        style.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));
        String casa="hola";
        GeoJsonSource icGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID,
                FeatureCollection.fromFeatures(new Feature[]{

                        Feature.fromGeometry(Point.fromLngLat(intermedio7.longitude(), intermedio7.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(destino.longitude(), destino.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(intermedio.longitude(), intermedio.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(intermedio2.longitude(), intermedio2.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(intermedio3.longitude(), intermedio3.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(intermedio4.longitude(), intermedio4.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(intermedio5.longitude(), intermedio5.latitude())),
                        Feature.fromGeometry(Point.fromLngLat(intermedio6.longitude(), intermedio6.latitude()))





                }));
        style.addSource(icGeoJsonSource);


    }

    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        if (response.body() == null) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            return;
        } else if (response.body().routes().size() < 1) {
            Toast.makeText(this, "error 2", Toast.LENGTH_LONG).show();
            return;
        }
        curreDirectionsRoute = response.body().routes().get(0);

        if (mapbox != null) {
            mapbox.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    GeoJsonSource source = (GeoJsonSource) style.getSource(ROUTE_SOURCE_ID);
                    if (source != null) {
                        source.setGeoJson(LineString.fromPolyline(curreDirectionsRoute.geometry(), Constants.PRECISION_6));
                    }
                }
            });
        }


    }

    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

    }

    private void ruta(MapboxMap mapboxMap, Point origenn, Point destinoo, Point intermedioo,Point intermedioo2,Point intermedioo3, Point intermedioo4, Point intermedioo5, Point intermedioo6) {



        client = MapboxDirections.builder()
                .origin(intermedio7)
                .addWaypoint(intermedio)
                .addWaypoint(intermedio2)
                .addWaypoint(intermedio3)
                .addWaypoint(intermedio4)
                .addWaypoint(intermedio5)
                .addWaypoint(intermedio6)
                .destination(destino)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(Mapbox.getAccessToken())
                .build();


        client.enqueueCall(this);



    }

    private void Ruta() {

        RouteOptions routeOptions;

        double a=locationComponent.getLastKnownLocation().getLongitude();
        double b=locationComponent.getLastKnownLocation().getLatitude();
        origen =Point.fromLngLat(a,b);
        //MapboxOptimization.builder().accessToken(getString(R.string.token)).coordinates(puntos).build();



        NavigationRoute.builder(getApplicationContext())
                .accessToken(getString(R.string.token))
                .origin(origen)
                .addWaypoint(intermedio7)
                .addWaypoint(intermedio)
                .addWaypoint(intermedio2)
                .addWaypoint(intermedio3)
                .addWaypoint(intermedio4)
                .addWaypoint(intermedio5)
                .addWaypoint(intermedio6)
                .destination(destino)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    private NavigationLauncherOptions options;

                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            Toast.makeText(Ruta1a.this, "error", Toast.LENGTH_LONG).show();
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Toast.makeText(Ruta1a.this, "error 2", Toast.LENGTH_LONG).show();

                        }
                        DirectionsRoute route = response.body().routes().get(0);



                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(route)
                                .shouldSimulateRoute(true)
                                .build();

                        NavigationLauncher.startNavigation(Ruta1a
                                .this, options);





                    }


                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alert !=null){
            alert.dismiss();
        }
        navigation.onDestroy();

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapbox.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    //enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public static boolean isGPSProvider(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("WrongConstant")
    @SuppressWarnings({"MissingPermission"})
    private void localizacion(@NonNull Style loadedMapStyle) {




        if(isGPSProvider(getApplicationContext())==false) {


            if (PermissionsManager.areLocationPermissionsGranted(this)) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿El gps esta desactivado lo desea activar?,Necesita activar el GPS para iniciar la ruta")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                locationComponent = mapbox.getLocationComponent();



                                locationComponent.activateLocationComponent(
                                        LocationComponentActivationOptions.builder(Ruta1a.this, loadedMapStyle).build());


                                locationComponent.setLocationComponentEnabled(true);



                                locationComponent.setCameraMode(CameraMode.TRACKING);


                                locationComponent.setRenderMode(RenderMode.COMPASS);

                                boton.setEnabled(true);


                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                dialogInterface.cancel();
                                boton.setEnabled(false);
                            }
                        });
                alert = builder.create();
                alert.show();


            } else {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(this);
            }
        }else{

            locationComponent = mapbox.getLocationComponent();



            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(Ruta1a.this, loadedMapStyle).build());


            locationComponent.setLocationComponentEnabled(true);



            locationComponent.setCameraMode(CameraMode.TRACKING);


            locationComponent.setRenderMode(RenderMode.COMPASS);
            boton.setEnabled(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
    }



    @Override
    protected void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }




}