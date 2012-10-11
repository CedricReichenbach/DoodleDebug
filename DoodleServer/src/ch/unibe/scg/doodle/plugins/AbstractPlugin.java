package ch.unibe.scg.doodle.plugins;


/**
 * Abstract Plugin class to manage and compute renderings of specified object
 * types. Subclasses need to implement methods of {@link RenderingPlugin} and
 * may override:
 * <ul>
 * <li>{@link #getClassAttribute()}</li>
 * <li>{@link #getSmallCSSKeyword()}</li>
 * <li>{@link #getObjectTypeName(Object)}</li>
 * <li>{@link #getCSS()}</li>
 * </ul>
 * 
 * @author Cedric Reichenbach
 * 
 */
public abstract class AbstractPlugin implements RenderingPlugin {

	/**
	 * {@inheritDoc} Default is java class name of the implementing plugin class
	 * (e.g. "ImagePlugin").
	 */
	@Override
	public String getClassAttribute() {
		return this.getClass().getSimpleName();
	}

	/**
	 * {@inheritDoc} Default: "smallRendering"
	 */
	@Override
	public String getSmallCSSKeyword() {
		return "smallRendering";
	}

	/**
	 * {@inheritDoc} Default: SimpleName of class (e.g. "Color"). This will be
	 * directly put into HTML code, so special characters may be written using
	 * their corresponind decimal/hex code, e.g. <code>&amp;#64;</code> for
	 * &#64;.<br>
	 */
	@Override
	public String getObjectTypeName(Object o) {
		// TODO: inspect generics using reflection
//		Collection<String> genericTypes = null;
//		String genericsString = "";
//		TypeVariable<?>[] typeParams = o.getClass().getTypeParameters();
//		if (typeParams.length > 0) {
//			genericTypes = new LinkedList<String>();
//			for (TypeVariable typeParam : typeParams) {
//				// search for fields using this typeParam
//				List<Field> fields = Arrays.asList(o.getClass().getFields());
//				for (Field field : fields) {
//					Type type = field.getGenericType();
//					if (type instanceof ParameterizedType) {
//						// TODO Now check if typeParam fits type
//					}
//				}
//			}
//		}
		return o.getClass().getSimpleName()/* + genericsString */;
	}

	@Override
	public String getCSS() {
		return "";
	}
}
