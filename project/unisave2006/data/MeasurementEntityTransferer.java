package unisave2006.data;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import unisave2006.data.value.MeasurementEntry;

public class MeasurementEntityTransferer implements Transferable {

	Vector<DataFlavor> flavors = new Vector<DataFlavor>();
	DataFlavor flavorsAray[] = null;
	Vector<MeasurementEntry> content = new Vector<MeasurementEntry>();
	
	public MeasurementEntityTransferer(Collection<MeasurementEntry> entries) {
		DataFlavor f;
		try {
			//f = new DataFlavor(String.class, "textSBS");
			//f = new DataFlavor("textSBS/tab-separated-values");
			f = new DataFlavor("textSBS/plain");
			//f = DataFlavor.getTextPlainUnicodeFlavor();	
			flavors.add(f);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		content.addAll(entries);
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
			if(flavor.equals(flavors.elementAt(0))){
				StringBuilder sb = new StringBuilder(content.size()*20);
				for(MeasurementEntry e: content){
					sb.append(e.getClipboardString());
					sb.append("\n");
				}
				return new ByteArrayInputStream(sb.toString().getBytes());
			}
		return null;
	}

	public DataFlavor[] getTransferDataFlavors() {
		if(flavorsAray == null){
			flavorsAray = new DataFlavor[flavors.size()];
			flavors.toArray(flavorsAray);
		}
		return flavorsAray;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavors.contains(flavor);
	}

}
