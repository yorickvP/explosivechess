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
  buildInputs = [ python numpy opencv makeWrapper ];
  src = ./.;
  builder = writeScript "builder.sh" ''
    source $stdenv/setup
    mkdir -p $out/bin
    cp $src/*.py $out/bin
    for i in `find $out/bin/ -type f -perm -111`; do
    wrapProgram $i \
            --prefix PYTHONPATH : "$(toPythonPath $out):$(toPythonPath ${numpy}):$(toPythonPath ${opencv})"
    done
  '';
}

