// class need not be public
class External {
    public void method(String text, boolean isTrue) {
        // @start region="sample"
        if (isTrue) {
            text = java.util.stream.Stream.of(text)
                .map(string -> new StringBuilder(string).reverse())
    		.collect(java.util.stream.Collectors.joining());
        }
	// @end
        System.out.println(text);
    }
}