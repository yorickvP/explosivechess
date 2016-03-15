with import <nixpkgs> {};

stdenv.mkDerivation {
  name = "nxj-dev";
  buildInputs = [ (import ./nxj.nix) ];
}
