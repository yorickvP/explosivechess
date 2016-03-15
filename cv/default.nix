let
  pkgs = import <nixpkgs> {};
  pypkgs = pkgs.python27Packages;
in
{ stdenv ? pkgs.stdenv, python ? pkgs.python27, 
  numpy ? pypkgs.numpy, bash ? pkgs.bash,
  opencv ? (pkgs.opencv3.override { enableBloat = true; })
}:

with pkgs; stdenv.mkDerivation {
  name = "chess";
  buildInputs = [ python numpy opencv ];
  src = ./.;
}

