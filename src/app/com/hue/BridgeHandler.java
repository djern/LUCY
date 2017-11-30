package app.com.hue;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateCacheType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedEvent;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.connection.HeartbeatManager;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryCallback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryResult;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.CompoundSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence.PresenceSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Scene;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBuilder;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridge;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridges;

import app.gui.Config;

public class BridgeHandler {
	
	private Bridge bridge;
	private HeartbeatManager heartbeatMng;
	private BridgeState bridgeState;
	private BridgeDiscovery bridgeDiscovery;
	private BridgeDiscoveryCallback bridgeDiscoveryCallback;
	private BridgeConnectionCallback bridgeConnectionCallback;
	@SuppressWarnings("unused")
	private List<BridgeDiscoveryResult> bridgeDiscoveryResults;
	private BridgeStateUpdatedCallback bridgeStateUpdatedCallback;
	
	public Date lastTriggerSZ = null;
	
	public BridgeHandler() {
		startCallbacks();	
	}

	/**
     * Use the KnownBridges API to retrieve the last connected bridge
     * @return Ip address of the last connected bridge, or null
     */
	public String getLastUsedBridgeIp() {
        List<KnownBridge> bridges = KnownBridges.getAll();

        if (bridges.isEmpty()) {
            return null;
        }

        return Collections.max(bridges, new Comparator<KnownBridge>() {
            @Override
            public int compare(KnownBridge a, KnownBridge b) {
                return a.getLastConnected().compareTo(b.getLastConnected());
            }
        }).getIpAddress();
    }

    /**
     * Start the bridge discovery search
     * Read the documentation on meethue for an explanation of the bridge discovery options
     */
	public void startBridgeDiscovery() {
        disconnectFromBridge();

        bridgeDiscovery = new BridgeDiscovery();
        bridgeDiscovery.search(BridgeDiscovery.BridgeDiscoveryOption.UPNP, bridgeDiscoveryCallback);

        System.out.println("Scanning the network for hue bridges...");
    }

    /**
     * Stops the bridge discovery if it is still running
     */
	public void stopBridgeDiscovery() {
        if (bridgeDiscovery != null) {
            bridgeDiscovery.stop();
            bridgeDiscovery = null;
        }
    	System.out.println("Stopping Search...");
    }
    
    /**
     * Use the BridgeBuilder to create a bridge instance and connect to it
     */
	public ReturnCode connectToBridge(String bridgeIp) {
        stopBridgeDiscovery();
        disconnectFromBridge();

        bridge = new BridgeBuilder("app name", "device name")
                .setIpAddress(bridgeIp)
                .setConnectionType(BridgeConnectionType.LOCAL)
                .setBridgeConnectionCallback(bridgeConnectionCallback)
                .addBridgeStateUpdatedCallback(bridgeStateUpdatedCallback)
                .build();

        System.out.println("Connecting to bridge (" + bridgeIp + ")...");
        return bridge.connect();
    }

    /**
     * Disconnect a bridge
     * The hue SDK supports multiple bridge connections at the same time,
     * but for the purposes of this demo we only connect to one bridge at a time.
     */
	public void disconnectFromBridge() {
        if (bridge != null) {
            bridge.disconnect();
            bridge = null;
        }
    }
   
	public void startCallbacks() {
    	
    	/**
         * The callback that receives the results of the bridge discovery
         */
        bridgeDiscoveryCallback = new BridgeDiscoveryCallback() {
            @Override
            public void onFinished(final List<BridgeDiscoveryResult> results, final ReturnCode returnCode) {
                // Set to null to prevent stopBridgeDiscovery from stopping it
                bridgeDiscovery = null;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (returnCode == ReturnCode.SUCCESS) {
                            bridgeDiscoveryResults = results;
                            System.out.println("Found " + results.size() + " bridge(s) in the network.");
                            int cnt = 1;
                            for (BridgeDiscoveryResult entry : results) {
                                System.out.println(cnt + ". Bridge: " + entry.getIP());
                                cnt++;
							}
                        } 
                        else if (returnCode == ReturnCode.STOPPED) {
                        	System.out.println("Bridge discovery stopped!");
                        } 
                        else {
                        	System.out.println("Error doing bridge discovery: " + returnCode);
                        }
                    }
                }).run();
            }
        };
        
        /**
         * The callback that receives bridge connection events
         */
        bridgeConnectionCallback = new BridgeConnectionCallback() {
            @Override
            public void onConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent) {
            	System.out.println("Connection event: " + connectionEvent);

                switch (connectionEvent) {
                    case LINK_BUTTON_NOT_PRESSED:
                    	System.out.println("Press the link button to authenticate.");
                        break;

                    case COULD_NOT_CONNECT:
                    	System.out.println("Could not connect.");
                        break;

                    case CONNECTION_LOST:
                    	System.out.println("Connection lost. Attempting to reconnect.");
                        break;

                    case CONNECTION_RESTORED:
                    	System.out.println("Connection restored.");
                        break;

                    case DISCONNECTED:
                        // User-initiated disconnection.
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list) {
                for (HueError error : list) {
                	System.out.println("Connection error: " + error.toString());
                }
            }
        };
        
        /**
         * The callback the receives bridge state update events
         */
        bridgeStateUpdatedCallback = new BridgeStateUpdatedCallback() {
            @Override
            public void onBridgeStateUpdated(Bridge bridge, BridgeStateUpdatedEvent bridgeStateUpdatedEvent) {
            	System.out.println("Bridge state updated event: " + bridgeStateUpdatedEvent);
                switch (bridgeStateUpdatedEvent) {
                    case INITIALIZED:
                        // The bridge state was fully initialized for the first time.
                        // It is now safe to perform operations on the bridge state.
                    	System.out.println("Connected!");
                    	// Starting HeartbeatManager
                        BridgeConnection connection = bridge.getBridgeConnection(BridgeConnectionType.LOCAL);
                        heartbeatMng = connection.getHeartbeatManager();
                        if(heartbeatMng == null) {
                        	System.out.println("Err: No Heartbeat!");
                        }
                        else {
                        	System.out.println("Starting Heartbeat!");
                        	heartbeatMng.startHeartbeat(BridgeStateCacheType.LIGHTS_AND_GROUPS, 1000);
                        	heartbeatMng.startHeartbeat(BridgeStateCacheType.SENSORS_AND_SWITCHES, 1000);
                        }
                        break;
                    case LIGHTS_AND_GROUPS:
                    	loadBrigde();
                    	break;
                    case SENSORS_AND_SWITCHES:
                    	loadBrigde();
                    	checkListeningTrigger();
                    	break;
                    default:
                        break;
                }
            }
        };
    }
	
	private void checkListeningTrigger() {
		CompoundSensor sensorSZ = (CompoundSensor) getSensor(Config.SENSORS_HUE_SZ_TRIGGER);
		PresenceSensor sensorPres = (PresenceSensor) sensorSZ.getDevices(DomainType.PRESENCE_SENSOR).get(0);
		lastTriggerSZ = sensorPres.getState().getLastUpdated();
	}
	
	public Date getLastTriggerSZ() {
		return lastTriggerSZ;
	}
	
	public boolean getStatus() {
		if (bridge == null)
			return false;
		else
			return bridge.isConnected();
	}
	
	public void loadBrigde() {
		bridgeState = bridge.getBridgeState();
	}
		
	public List<LightPoint> loadLights() {
		return bridgeState.getLightPoints();
	}
	
	public LightPoint getLight(String id) {
		return bridgeState.getLightPoint(id);
	}

	public List<Sensor> loadSensors(){
		return bridgeState.getSensors();
	}
	
	public Sensor getSensor(String id) {
		return bridgeState.getSensor(id);
	}
	
	public List<Scene> loadScenes() {
		return bridgeState.getScenes();
	}

	public Scene getScene(String ident) {
		return bridgeState.getScene(ident);
	}
	
	@SuppressWarnings("deprecation")
	public void setScene(String ident) {
		getScene(ident).setIsActive(true);
		bridge.updateState(bridgeState);
	}
}
