package com.example.descubremadrid;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.api.matching.v5.MapboxMapMatching;
import com.mapbox.api.matching.v5.models.MapMatchingResponse;
import com.mapbox.api.optimization.v1.MapboxOptimization;
import com.mapbox.api.optimization.v1.models.OptimizationResponse;
import com.mapbox.core.exceptions.ServicesException;
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
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.navigation.OfflineCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.graphics.BitmapFactory.decodeResource;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;


public class navegacionOptimizada extends AppCompatActivity implements OnMapReadyCallback,MapboxMap.OnMapClickListener, MapboxMap.OnMapLongClickListener {

    private static final String ICON_GEOJSON_SOURCE_ID = "icon-source-id";
    private static final String FIRST = "first";
    private static final String ANY = "any";
    private static final String ROUTE_SOURCE_ID = "route-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    DirectionsRoute currentRoute;

    private static final String TEAL_COLOR = "#0000ff";
    private static final float POLYLINE_WIDTH = 5;
    Point p = Point.fromLngLat(-3.6920929873468222, 40.414439560352825);
    Style style;
    public static NavigationLauncherOptions options;
    private PermissionsManager permissionsManager;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private static DirectionsRoute optimizedRoute;
    private static MapboxOptimization optimizedClient;
    LocationComponent locationComponent;
    private List<Point> stops = new ArrayList<>();
    private Point origin;
    List<Feature> paradas;
    MapboxMapMatching mapboxMapMatching;
    Button boton;
    Point origen;
    LatLng point;
    Point destino = Point.fromLngLat(-3.678548, 40.353166);
    Point intermedio = Point.fromLngLat(-3.658664, 40.392200);
    Point intermedi3 = Point.fromLngLat(-3.69518289205938, 40.416792095268136);
    Point intermedi4 = Point.fromLngLat(-3.697586151280258, 40.417412889376166);
    Point cinco = Point.fromLngLat(-3.6920929873468222, 40.414439560352825);
    List<Point> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lista=new ArrayList<>();
        lista = (ArrayList<Point>) getIntent().getSerializableExtra("puntos");


        Mapbox.getInstance(this, getString(R.string.token));


        setContentView(R.layout.activity_navegacion_optimizada);
        boton = findViewById(R.id.botonRu);




        primerPunto();


        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerRuta();

            }
        });

    }

    private void obtenerRuta() {

        double a=locationComponent.getLastKnownLocation().getLongitude();
        double b=locationComponent.getLastKnownLocation().getLatitude();
        origen=Point.fromLngLat(a,b);




    NavigationRoute.builder(getApplicationContext())
            .accessToken(getString(R.string.token))
            .origin(origen)
            .addWaypoint(lista.get(0))
            .addWaypoint(lista.get(1))
            .destination(lista.get(2))
            .alternatives(true)
            .profile(DirectionsCriteria.PROFILE_WALKING)
            .build()
            .getRoute(new Callback<DirectionsResponse>() {
                @Override
                public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                    if (response.body() == null) {
                        Toast.makeText(navegacionOptimizada.this, "error", Toast.LENGTH_LONG).show();
                        return;
                    } else if (response.body().routes().size() < 1) {
                        Toast.makeText(navegacionOptimizada.this, "error 2", Toast.LENGTH_LONG).show();

                    }
                    DirectionsRoute route = response.body().routes().get(0);


                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                            .directionsRoute(route)
                            .shouldSimulateRoute(true)
                            .build();

                    NavigationLauncher.startNavigation(navegacionOptimizada
                            .this, options);
                }

                @Override
                public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                }
            });


}





    @SuppressLint("WrongConstant")
    @SuppressWarnings({"MissingPermission"})
    private void localizacion(@NonNull Style loadedMapStyle) {

        if(isGPSProvider(getApplicationContext())==true){
            locationComponent = mapboxMap.getLocationComponent();

            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(navegacionOptimizada.this, loadedMapStyle).build());

            locationComponent.setLocationComponentEnabled(true);

            locationComponent.setCameraMode(CameraMode.TRACKING);


            locationComponent.setRenderMode(RenderMode.COMPASS);

            boton.setEnabled(true);
        }else{
            if (PermissionsManager.areLocationPermissionsGranted(getApplicationContext())) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿El gps esta desactivado lo desea activar?,Necesita activar el GPS para iniciar la ruta")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                locationComponent = mapboxMap.getLocationComponent();

                                locationComponent.activateLocationComponent(
                                        LocationComponentActivationOptions.builder(navegacionOptimizada.this, loadedMapStyle).build());

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
                AlertDialog alert = builder.create();
                alert.show();


            } else {
                permissionsManager = new PermissionsManager((PermissionsListener) this);
                permissionsManager.requestLocationPermissions(this);
            }
        }




    }

    public static boolean isGPSProvider(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetowrkProvider(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                // Add origin and destination to the mapboxMap


                marcador(style);
                rutaOptimizadaLinea(style);



                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(origin.latitude(), origin.longitude()))
                        .zoom(10)
                        .tilt(13)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));



                //Toast.makeText(Mapboxnavigation.this, R.string.click_instructions, Toast.LENGTH_SHORT).show();
                //mapboxMap.addOnMapClickListener(Mapboxnavigation.this);
                //mapboxMap.addOnMapLongClickListener(Mapboxnavigation.this);
                localizacion(style);
                double a=-3.00;
                // Toast.makeText(getApplicationContext(), ""+a, Toast.LENGTH_SHORT).show();
                for(int i=0;i<lista.size();i++){
                    generarLugares(lista.get(i));
                }


                /*p(destino);
                p(intermedi3);
                p(cinco);
                p(intermedi4);*/


            }
        });
    }
     public void generarPMapa(){
         for(int i=0;i<lista.size();i++){
             generarLugares(lista.get(i));
         }
     }
    public void marcador(@NonNull Style loadedMapStyle) {
        // Add the marker image to map
        loadedMapStyle.addImage("icon-image", decodeResource(
                this.getResources(), R.drawable.mapbox_marker_icon_default));

        // Add the source to the map

        loadedMapStyle.addSource(new GeoJsonSource(ICON_GEOJSON_SOURCE_ID,
                Feature.fromGeometry
                        (Point.fromLngLat(origin.longitude(), origin.latitude()))));





        loadedMapStyle.addLayer(new SymbolLayer("icon-layer-id", ICON_GEOJSON_SOURCE_ID).withProperties(
                iconImage("icon-image"),
                iconSize(1f),
                iconAllowOverlap(true),
                iconIgnorePlacement(true),
                iconOffset(new Float[] {0f, -7f})
        ));
    }
    public void rutaOptimizadaLinea(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource("optimized-route-source-id"));
        loadedMapStyle.addLayerBelow(new LineLayer("optimized-route-layer-id", "optimized-route-source-id")
                .withProperties(
                        lineColor(Color.parseColor(TEAL_COLOR)),
                        lineWidth(POLYLINE_WIDTH)
                ), "icon-layer-id");
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        // Optimization API is limited to 12 coordinate sets
        if (doceLugares()) {
            Toast.makeText(navegacionOptimizada.this,"solo 12", Toast.LENGTH_LONG).show();
        } else {
            style = mapboxMap.getStyle();
            if (style != null) {
                anadirLugar(style, intermedi3);
                anadirPunto(intermedi3);
                obtenerROptimi(style, stops);

            }
        }
        return true;
    }

    public void generarLugares(Point punto){

            style = mapboxMap.getStyle();
            if (style != null) {
                anadirLugar(style, punto);
                anadirPunto(punto);
                obtenerROptimi(style, stops);


        }
    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        stops.clear();
        if (mapboxMap != null) {
            style = mapboxMap.getStyle();
            if (style != null) {
                eliminarMarcadores(style);
                eliminarRuta(style);
                primerPunto();

                return true;
            }
        }
        return false;
    }

    private void eliminarMarcadores(@NonNull Style styleE) {
        GeoJsonSource optimizedLineSource = style.getSourceAs(ICON_GEOJSON_SOURCE_ID);
        if (optimizedLineSource != null) {
            optimizedLineSource.setGeoJson(Point.fromLngLat(origin.longitude(), origin.latitude()));
        }
    }

    private void eliminarRuta(@NonNull Style styleE) {
        GeoJsonSource optimizedLineSource = style.getSourceAs("optimized-route-source-id");
        if (optimizedLineSource != null) {
            optimizedLineSource.setGeoJson(FeatureCollection.fromFeatures(new Feature[] {}));
        }
    }

    private boolean doceLugares() {
        return stops.size() == 12;
    }

    private void anadirLugar(@NonNull Style styleE, Point point) {

        paradas = new ArrayList<>();
        for (Point singlePoint : stops) {
            paradas.add(Feature.fromGeometry(
                    Point.fromLngLat(singlePoint.longitude(), singlePoint.latitude())));
        }
        paradas.add(Feature.fromGeometry(Point.fromLngLat(point.longitude(), point.latitude())));

        GeoJsonSource iconSource = style.getSourceAs(ICON_GEOJSON_SOURCE_ID);

        if (iconSource != null) {
            iconSource.setGeoJson(FeatureCollection.fromFeatures(paradas));
        }
    }

    private void anadirPunto(Point point) {
        stops.add(Point.fromLngLat(point.longitude(), point.latitude()));


    }

    private void primerPunto() {
        // Set first stop

        origin = lista.get(0);


        stops.add(lista.get(0));

    }

    private void obtenerROptimi(@NonNull final Style styleE, List<Point> coordinates) {
        optimizedClient = MapboxOptimization.builder()
                .source(FIRST)
                .destination(ANY)
                .coordinates(coordinates)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.token))
                .build();

        optimizedClient.enqueueCall(new Callback<OptimizationResponse>() {
            @Override
            public void onResponse(@NotNull Call<OptimizationResponse> call, @NotNull Response<OptimizationResponse> response) {
                if (!response.isSuccessful()) {
                    Timber.d(getString(R.string.no_success));
                    Toast.makeText(navegacionOptimizada.this, "no ok", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body() != null) {
                        List<DirectionsRoute> routes = response.body().trips();
                        if (routes != null) {
                            if (routes.isEmpty()) {
                                Timber.d("%s size = %s", getString(R.string.successful_but_no_routes), routes.size());
                                Toast.makeText(navegacionOptimizada.this, R.string.successful_but_no_routes,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Get most optimized route from API response
                                optimizedRoute = routes.get(0);

                                //Toast.makeText(getApplicationContext(),""+optimizedRoute.toString(),Toast.LENGTH_LONG).show();
                                pintarRuta(style, optimizedRoute);





                            }
                        } else {
                            Timber.d("list of routes in the response is null");
                            Toast.makeText(navegacionOptimizada.this, String.format(getString(R.string.null_in_response),
                                    "The Optimization API response's list of routes"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Timber.d("response.body() is null");
                        Toast.makeText(navegacionOptimizada.this, String.format(getString(R.string.null_in_response),
                                "The Optimization API response's body"), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<OptimizationResponse> call, @NotNull Throwable throwable) {
                Timber.d("Error: %s", throwable.getMessage());
            }
        });
    }




    private void pintarRuta(@NonNull Style styleE, DirectionsRoute route) {
        GeoJsonSource optimizedLineSource = style.getSourceAs("optimized-route-source-id");
        if (optimizedLineSource != null) {
            optimizedLineSource.setGeoJson(FeatureCollection.fromFeature(Feature.fromGeometry(
                    LineString.fromPolyline(route.geometry(), PRECISION_6))));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the directions API request

        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



}
