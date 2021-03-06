/*
 *     This file is part of Discord4J.
 *
 *     Discord4J is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Discord4J is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Discord4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package sx.blah.discord.util.audio.processors;

import sx.blah.discord.handle.audio.AudioEncodingType;
import sx.blah.discord.handle.audio.IAudioProcessor;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.handle.audio.impl.DefaultProvider;

/**
 * This processor implementation allows for audio providers to be paused.
 */
public class PauseableProcessor implements IAudioProcessor {

	private volatile IAudioProvider provider = new DefaultProvider();
	private volatile boolean isPaused = false;

	/**
	 * This gets whether this processor is paused.
	 *
	 * @return True if paused, false if otherwise.
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * This sets whether this processor is paused.
	 *
	 * @param isPaused True to pause, false to resume.
	 */
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	@Override
	public boolean setProvider(IAudioProvider provider) {
		this.provider = provider;
		return true;
	}

	@Override
	public boolean isReady() {
		return provider.isReady() && !isPaused;
	}

	@Override
	public byte[] provide() {
		if (!isPaused) {
			return provider.provide();
		}
		return new byte[0];
	}

	@Override
	public int getChannels() {
		return provider.getChannels();
	}

	@Override
	public AudioEncodingType getAudioEncodingType() {
		return provider.getAudioEncodingType();
	}
}
