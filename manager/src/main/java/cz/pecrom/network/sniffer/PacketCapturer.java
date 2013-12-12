package cz.pecrom.network.sniffer;

import cz.pecrom.network.sniffer.exceptions.*;
import cz.pecrom.network.sniffer.packetcapturesupport.*;
import org.jnetpcap.*;
import org.jnetpcap.packet.*;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 12.12.13
 * Time: 13:38
 */
public class PacketCapturer implements PcapPacketHandler{
  public static final String PACKET_CAPTURED = "packetCaptured";

  private final PacketCapturedSupport packetCapturedSupport = new PacketCapturedSupport();
  private int snaplen = 64 * 1024;
  private int flags = Pcap.MODE_PROMISCUOUS;
  int timeout = 10 * 1000;
  private State state = State.STOP;
  private Pcap pcap = null;
  private PcapIf adapter;
  private StringBuilder errBuf;
  private Capturer capturer;

  public PacketCapturer(PcapIf adapter) {
    this.adapter = adapter;
    errBuf = new StringBuilder();
  }

  public PcapIf getAdapter() {
    return adapter;
  }

  public void setAdapter(PcapIf adapter) throws CaptureInProgressException {
    if(getState()==State.CAPTURE)
      throw new CaptureInProgressException(getAdapter(), "Cannot change adapter during capturing");
    this.adapter = adapter;

  }

  public void setState(State newState){
    if(!state.equals(newState) && newState.equals(State.CAPTURE)){
      pcap = Pcap.openLive(adapter.getName(), snaplen, flags, timeout, errBuf);
      capturer = new Capturer(pcap, this);
      capturer.start();
    }
    else if(!state.equals(newState) && newState.equals(State.STOP) && capturer!=null){
      capturer.stop();
      capturer = null;
    }

    if(!state.equals(newState))
      this.state = newState;

  }

  public State getState(){
    return state;
  }

  @Override
  public void nextPacket(PcapPacket packet, Object o) {
    System.out.println("packet captured");
    packetCapturedSupport.firePacketCaptured(getAdapter(), packet);
  }

  public void addPacketCapturedListener(PacketCapturedListener listener){
    packetCapturedSupport.addPacketCapturedListener(listener);
  }

  public void removePacketCapturedListener(PacketCapturedListener listener){
    packetCapturedSupport.removePacketCapturedListener(listener);
  }

  public static enum State{
    CAPTURE, STOP
  }

  private class Capturer extends SwingWorker<Void, Void>{
    private Pcap pcap;
    private boolean capture = false;
    private PcapPacketHandler handler;

    private Capturer(Pcap pcap, PcapPacketHandler handler) {
      this.pcap = pcap;
      this.handler = handler;
    }

    public void start(){
      capture = true;
      this.execute();
    }

    public void stop(){
      pcap.close();
      capture = false;
    }

    @Override
    protected Void doInBackground() throws Exception {

      pcap.loop(0, handler, "test");



      return null;
    }
  }
}
