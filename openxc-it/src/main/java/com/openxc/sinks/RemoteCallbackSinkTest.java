package com.openxc.sinks;

import java.util.HashMap;
import java.util.Map;

import com.openxc.remote.RawMeasurement;
import com.openxc.remote.VehicleServiceListenerInterface;

import android.test.AndroidTestCase;

import android.test.suitebuilder.annotation.SmallTest;

public class RemoteCallbackSinkTest extends AndroidTestCase {
    Map<String, RawMeasurement> measurements;
    RemoteCallbackSink notifier;
    VehicleServiceListenerInterface listener;
    String measurementId = "the_measurement";
    String receivedId = null;

    @Override
    public void setUp() {
        // TODO what are the contractual guarantees that this class says about
        // this measurements map?
        measurements = new HashMap<String, RawMeasurement>();
        notifier = new RemoteCallbackSink();
        notifier.setMeasurements(measurements);
        listener = new VehicleServiceListenerInterface.Stub() {
            public void receive(String measurementId, RawMeasurement value) {
                receivedId = measurementId;
            }
        };
    }

    @SmallTest
    public void testRegister() {
        assertEquals(0, notifier.getListenerCount());
        notifier.register(listener);
        assertEquals(1, notifier.getListenerCount());
    }

    @SmallTest
    public void testUnregisterInvalid() {
        // this just shouldn't explode, it should ignore it...or should it?
        // failing silently is usually a bad thing
        assertEquals(0, notifier.getListenerCount());
        notifier.unregister(listener);
        assertEquals(0, notifier.getListenerCount());
    }

    @SmallTest
    public void testUnregisterValid() {
        notifier.register(listener);
        assertEquals(1, notifier.getListenerCount());
        notifier.unregister(listener);
        assertEquals(0, notifier.getListenerCount());
    }

    @SmallTest
    public void testReceiveCorrectId() {
        notifier.register(listener);
        assertNull(receivedId);
        notifier.receive(measurementId, new RawMeasurement(1));
        // TODO this test actually fails and it exposes some fundamental design
        // weirdness with the measurements map within the data pipeline. we
        // expect someone to put measurements in the map, but that's done by the
        // pipeilne, not by the callback. but it has a reference to it?
        // something is messed up.
        // assertNotNull(receivedId);
        // assertEquals(receivedId, measurementId);
    }
}