#include <string>
#include "CTree.h"

class CInterface
{
private:
    CTree tree;

    void executeCommand(const std::string &line);
    void handleEnter(const std::string &formula);
    void handleJoin(const std::string &formula);
    void handleComp(const std::string &arguments);
    void printCorrections(const CTree &parsedTree);

public:
    CInterface();

    void run();
};