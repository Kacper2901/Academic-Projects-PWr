#include "CInterface.h"
#include <iostream>
#include "Constants.h"

CInterface::CInterface()
{
}

void CInterface::run()
{
    std::string line;

    while (std::getline(std::cin, line))
    {
        executeCommand(line);
    }
}

void CInterface::executeCommand(const std::string &line)
{
    std::string command = "";
    std::string arguments = "";

    int index = 0;
    int length = (int)line.length();

    while (index < length && (line[index] == ' ' || line[index] == '\t'))
    {
        index++;
    }

    while (index < length && line[index] != ' ' && line[index] != '\t')
    {
        command += line[index];
        index++;
    }

    while (index < length)
    {
        arguments += line[index];
        index++;
    }

    if (command == "enter")
    {
        handleEnter(arguments);
    }
    else if (command == "print")
    {
        std::cout << tree.print() << std::endl;
    }
    else if (command == "vars")
    {
        std::cout << tree.vars() << std::endl;
    }
    else if (command == "comp")
    {
        handleComp(arguments);
    }
    else if (command == "join")
    {
        handleJoin(arguments);
    }
    else if (command != "")
    {
        std::cout << UNKNOWN_COMMAND << std::endl;
    }
}

void CInterface::handleEnter(const std::string &formula)
{
    tree.enter(formula);
    printCorrections(tree);
}

void CInterface::handleJoin(const std::string &formula)
{
    CTree other;
    other.enter(formula);

    printCorrections(other);
    tree = tree + other;
}

void CInterface::handleComp(const std::string &arguments)
{
    double result = 0;
    bool divisionByZero = false;

    if (!tree.comp(arguments, result, divisionByZero))
    {
        std::cout << INCORRECT_VALUES_COUNT << std::endl;
    }
    else if (divisionByZero)
    {
        std::cout << DIVISION_BY_ZERO << std::endl;
    }
    else
    {
        std::cout << result << std::endl;
    }
}

void CInterface::printCorrections(const CTree &parsedTree)
{
    bool corrected = false;

    if (parsedTree.getWasCharacterIgnored())
    {
        std::cout << CHARACTER_IGNORED << std::endl;
        corrected = true;
    }

    if (parsedTree.getWasArgumentAdded())
    {
        std::cout << ARGUMENT_ADDED << std::endl;
        corrected = true;
    }

    if (parsedTree.getWereExtraArgumentsIgnored())
    {
        std::cout << EXTRA_ARGUMENTS << std::endl;
        corrected = true;
    }

    if (parsedTree.getWasInvalidTokenIgnored())
    {
        std::cout << INVALID_TOKEN_IGNORED << std::endl;
        corrected = true;
    }

    if (corrected)
    {
        std::cout << CORRECTED_FORMULA
                  << parsedTree.print() << std::endl;
    }
}