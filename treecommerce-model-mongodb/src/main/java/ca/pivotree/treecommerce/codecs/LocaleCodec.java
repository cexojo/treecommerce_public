package ca.pivotree.treecommerce.codecs;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Created by cexojo on 18/04/2020
 */
public class LocaleCodec implements Codec<Locale> {
	@Override
	public Class<Locale> getEncoderClass() {
		return Locale.class;
	}

	@Override
	public void encode(BsonWriter bsonWriter, Locale locale, EncoderContext encoderContext) {
		bsonWriter.writeString(locale.toString());
	}

	@Override
	public Locale decode(BsonReader bsonReader, DecoderContext decoderContext) {
		String value = bsonReader.readString();
		if (StringUtils.isNotBlank(value)) {
			return new Locale(value);
		}

		return null;
	}
}