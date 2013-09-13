exports.produce = function() {
  return new org.jasmine.intellij.Execution({
    name: function() { return "fooSpec.js" },
    main: function() { return "org.jasmine.cli.Main" },
    arguments: function() { return "--compile-mode OFF --pattern **/*Spec.js"; }
  })
}
