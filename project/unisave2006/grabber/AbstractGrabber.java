/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.grabber;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.xml.sax.Attributes;

import unisave2006.data.Measurement;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.units.UnitDescription;

public abstract class AbstractGrabber implements GrabberInterface {

	public void setData(String uri, String name, String qName, Attributes atts) {
		// nothing to do
	}

	public AbstractGrabber() {
		
	}
	
	protected void copyTo(AbstractGrabber g) {
		g.measurement = measurement;
		g.posibleUnits = posibleUnits;
	}

	protected List<UnitDescription> posibleUnits = new ArrayList<UnitDescription>();

	public List<UnitDescription> getPosibleUnits() {
		return posibleUnits;
	}
	
	public void setPosibleUnits(List<UnitDescription> posibleUnits) {
		this.posibleUnits = posibleUnits;
	}

	public void startGrabbingInNesstedMode(InputStream in, InputStream err,
			Measurement m) throws IOException {
		setInputStream(in);
		measurement = m;
		stopped = false;
	}

	public abstract void parse();

	protected Thread grabbingThread = null;

	protected boolean stopped = true;

	protected Reader inputReder = null;

	protected Thread transfererThread;

	protected ConcurrentLinkedQueue<MeasurementEntry> queue = null;

	protected Measurement measurement;

	protected InputStream inStram;

	public Measurement getMeasurement() {
		return measurement;
	}
	
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	
	public void setReader(Reader r) {
		inputReder = r;
	}

	public boolean isRunning() {
		if (grabbingThread == null)
			return false;
		return grabbingThread.isAlive();
	}

	public void startGrabbing(InputStream in, InputStream err, Measurement m)
			throws IOException {
		setInputStream(in);
		measurement = m;
		createEntityBuffer();
		stopped = false;

		transfererThread = new Thread(new Runnable() {
			public void run() {
				transferEntities();
			}
		});
		transfererThread.start();

		grabbingThread = new Thread(new Runnable() {
			public void run() {
				parse();
			}
		});
		grabbingThread.start();
	}

	protected void transferEntities() {
		ArrayList<MeasurementEntry> vect = new ArrayList<MeasurementEntry>(100);
		MeasurementEntry e = null;
		while (!stopped) {
			do {
				e = queue.poll();
				if (e != null)
					vect.add(e);
			} while (e != null);
			if (vect.size() > 0) {
				measurement.addEntries(vect);
				vect.clear();
			}
			try {
				Thread.sleep(600);
			} catch (InterruptedException e1) {
			}
		}

	}

	protected void createEntityBuffer() {
		queue = new ConcurrentLinkedQueue<MeasurementEntry>();
	}

	public void stopGrabbing() {
		stopped = true;
		try {
			if (inStram != null)
				inStram.close();
		} catch (IOException e) {

		}
	}

	public void setInputStream(InputStream is) {
		inStram = is;
		if (inputReder == null) {
			try {
				setReader(new InputStreamReader(is, "windows-1250"));
			} catch (UnsupportedEncodingException e) {
				setReader(new InputStreamReader(is));
			}
		}
	}

}
