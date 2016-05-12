#!/bin/bash

if [ "$1" == "-i" ]; then
    echo -n "Test name: "
    read name
    echo -n "Total number of generations: "
    read generations
    generation=0
    tmp="$(mktemp)"
    while [ $generation -lt $generations ]; do
        nano "$tmp"
        mkdir -p "$(dirname "$0")/../resources/simulatorTests/$name/$generation/default"
        "$0" "$tmp" "$(dirname "$0")/../resources/simulatorTests/$name/$generation/default/0,0"
        generation=$(($generation+1))
    done
    cat <<EOF
    @Test
    public void ${name}Test() throws IOException {
        test("$name", $generations, new Rectangle(0, 0, 1, 1), true, "default");
    }
EOF
    exit
fi

if [ $# -ne 2 ]; then
    echo "Invalid arguments (in arg 1, out arg 2)"
fi

if [ ! -f "$1" ]; then
    echo "Invalid file input (argument 1)"
    exit
fi

function sedPattern() {
    echo -e "s,-$1,$2,g"
}

i=0
hyphenate="s,-\([ #][ #][ #][ #]\)\([^-]\),-\1-\2,g"
cat "$1" | sed -e "s, ,*,g" | (while read line; do printf "%-64s\n" "$line" | sed -e "s,*, ,g"; done) | sed \
    -e "s,^\(....\),-\1-," \
    -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" \
    -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" \
    -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" \
    -e "$hyphenate" -e "$hyphenate" -e "$hyphenate" \
    -e "$(sedPattern "    " 0)" \
    -e "$(sedPattern "   #" 1)" \
    -e "$(sedPattern "  # " 2)" \
    -e "$(sedPattern "  ##" 3)" \
    -e "$(sedPattern " #  " 4)" \
    -e "$(sedPattern " # #" 5)" \
    -e "$(sedPattern " ## " 6)" \
    -e "$(sedPattern " ###" 7)" \
    -e "$(sedPattern "#   " 8)" \
    -e "$(sedPattern "#  #" 9)" \
    -e "$(sedPattern "# # " a)" \
    -e "$(sedPattern "# ##" b)" \
    -e "$(sedPattern "##  " c)" \
    -e "$(sedPattern "## #" d)" \
    -e "$(sedPattern "### " e)" \
    -e "$(sedPattern "####" f)" \
    | (while read line; do echo "$(printf "%7s" "$(echo "obase=16;$i" | bc)" | sed -e "s, ,0,g"): $line"; i=$(($i+8)); done) \
    | xxd -c 8 -g 8 -r > "$2"
tmp="$(mktemp)"
dd if=/dev/zero of="$tmp" bs=8 count=1
while [ $(stat "-c%s" "$2") -lt 512 ]; do
    cat "$tmp" >> "$2"
done
rm "$tmp"
cat "$1"
printf "%*s\n" "${COLUMNS:-$(tput cols)}" "" | sed -e "s, ,-,g" | grep --color -
xxd -c 8 -b "$2"
