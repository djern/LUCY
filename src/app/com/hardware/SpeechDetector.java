package app.com.hardware;

public class SpeechDetector {

	private double threshold;
	private float[] floatArr;
	private double dBSPL;
	private long lastDetection = 0, firstDetection = 0, momentDetection = 0;
	private boolean speaking = false;
	private double duration;
	
	public SpeechDetector(double threshold) {
		this.threshold = threshold;
		lastDetection = System.currentTimeMillis();
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public boolean checkIfSpeaking(byte[] targetData){

		int len = targetData.length/2;
		double pause = 0.66;
		floatArr = new float[len];
		floatArr = toFloatArray(targetData, 0, floatArr, 0, len);
		dBSPL = soundPressureLevel(floatArr);
		
		if(dBSPL > threshold){
			momentDetection = System.currentTimeMillis();
			if(speaking == false) {
				firstDetection = momentDetection;
				lastDetection = momentDetection;
			}
			if(((momentDetection - lastDetection)/1000 <= pause) && speaking == false) {
				speaking = true;
			}
			else if(((momentDetection - lastDetection)/1000 <= pause) && speaking == true) {
				lastDetection = momentDetection;
//				System.out.println("Sound detected for:" + (double)(momentDetection - firstDetection)/1000 + " seconds, " + (int)dBSPL + "dB SPL\n");
				duration = (double)(momentDetection - firstDetection)/1000;
			}
		}
		else if(dBSPL <= threshold && (System.currentTimeMillis() - lastDetection)/1000 <= pause)
			speaking = true;
		else
			speaking = false;
		return speaking;
	}
	
	public double getDurationInSec() {
		return duration;
	}
	
	/**
	 * Calculates the local (linear) energy of an audio buffer.
	 * 
	 * @param buffer
	 *            The audio buffer.
	 * @return The local (linear) energy of an audio buffer.
	 */
	private double localEnergy(final float[] buffer) {
		double power = 0.0D;
		for (float element : buffer) {
			power += element * element;
		}
		return power;
	}

	/**
	 * Returns the dBSPL for a buffer.
	 * 
	 * @param buffer
	 *            The buffer with audio information.
	 * @return The dBSPL level for the buffer.
	 */
	private double soundPressureLevel(final float[] buffer) {
		double value = Math.pow(localEnergy(buffer), 0.5);
		value = value / buffer.length;
		return linearToDecibel(value);
	}

	/**
	 * Converts a linear to a dB value.
	 * 
	 * @param value
	 *            The value to convert.
	 * @return The converted value.
	 */
	private double linearToDecibel(final double value) {
		return 20.0 * Math.log10(value);
	}
	
	public float[] toFloatArray(byte[] in_buff, int in_offset,
            float[] out_buff, int out_offset, int out_len) {
		int ix = in_offset;
        int len = out_offset + out_len;
        for (int ox = out_offset; ox < len; ox++) {
            out_buff[ox] = ((short) ((in_buff[ix++] & 0xFF) | 
                       (in_buff[ix++] << 8))) * (1.0f / 32767.0f);
        }

        return out_buff;
    }
}
