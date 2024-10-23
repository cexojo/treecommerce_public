package ca.pivotree.treecommerce.codecs.provider;

import ca.pivotree.treecommerce.codecs.LocaleCodec;
import java.util.Locale;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by cexojo on 18/04/2020
 */

@SuppressWarnings({"unchecked", "unused"})
public class LocaleCodecProvider implements CodecProvider {
	@Override
	public <T> Codec<T> get(Class<T> theClass, CodecRegistry registry) {
		if (Locale.class.equals(theClass)) {
			return (Codec<T>) new LocaleCodec();
		}

		return null;
	}
}
