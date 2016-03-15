# install: nix-env -if nxj.nix

with import <nixpkgs> {};

stdenv.mkDerivation rec {
  version = "0.9.1beta-3";
  builder = ./nix_builder.sh;
  tarname = "leJOS_NXJ_${version}";
  name = "nxj-${version}";
  src = fetchurl { 
  	url  = "mirror://sourceforge/nxt.lejos.p/${version}/${tarname}.tar.gz";
  	sha256 = "18ll9phbl1i2dasici1m8jprcfhzl03dq0h1dsdl9iwq1yv380pi";
  };
  JDK_PATH = jdk;

  buildInputs = [ jdk ant libusb makeWrapper ];
}
