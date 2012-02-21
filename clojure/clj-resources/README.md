# clj-resources

Simple wrapper around java.util.ResourceBundle and java.text.MessageFormat.

## Usage

<pre><code>
;
;#MyResource_de.properties
;my.message.key=message with '{0}', '{1}' and '{2}' as arguments
;

(def resource (load-resource "MyResource" (Locale. "de")))

(resource "my.message.key" "some" "format" "arguments")
; message with 'some', 'format' and 'arguments' as arguments

</code></pre>

## License

Copyright (C) 2012 floppywaste

Distributed under the Eclipse Public License, the same as Clojure.
